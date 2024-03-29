package com.alercom.app.ui.alerts.create

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils

import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alercom.app.MainActivity
import com.alercom.app.R
import com.alercom.app.adapter.TypeEventAdapter
import com.alercom.app.data.model.EventType
import com.alercom.app.data.model.Reference
import com.alercom.app.databinding.CreateAlertFragmentBinding
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.resources.DatePickerFragment
import com.alercom.app.resources.MapViewFragment
import com.alercom.app.resources.MapsActivity
import com.alercom.app.resources.RealPathUtil
import com.app.alercom.adapter.AffectsRangeSpinnerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.action_bar_toolbar.view.*
import kotlinx.android.synthetic.main.edit_user_fragment.*
import kotlinx.android.synthetic.main.loading.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class CreateAlertFragment  : Fragment() {

    companion object {
        fun newInstance() = CreateAlertFragment()
    }
    private val args: CreateAlertFragmentArgs by navArgs()
    private lateinit var viewModel: CreateAlertViewModel
    private var rangeAdapter :  AffectsRangeSpinnerAdapter? = null
    private var typeEventAdapter :  TypeEventAdapter? = null
    private var _binding: CreateAlertFragmentBinding?  = null
    private val binding get() = _binding!!
    private var _range : Reference? = null
    private var _eventType: EventType? = null
    private var image :File? = null
    private var currentPhotoPath: String? = null
    var latestTmpUri : Uri? = null
    private val RESULT_MAP = 2
    private val REQUEST_PERMISSION_CAMERA = 100
    private val REQUEST_PERMISSION_FILE = 200
    private var latitude :Double? = null
    private var longitude :Double? = null

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_amin) }

    private var clicked : Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = this.requireActivity().actionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = CreateAlertFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, CreateAlertViewModelFactory())
            .get(CreateAlertViewModel::class.java)
        image = null
        viewModel.getAffectsRange()
        viewModel.getEventTypes(args.eventTypeId)
        viewModel.rangesResult.observe(this@CreateAlertFragment, Observer {
            val eventResult = it ?: return@Observer
            if (eventResult.success != null) {
                rangeAdapter = AffectsRangeSpinnerAdapter(requireContext(),eventResult.success) //{ eventType -> onClickListener(eventType)}
                _binding?.afectationsRangeId?.setAdapter(rangeAdapter)
            }
        })

        viewModel.eventTypeResult.observe(this@CreateAlertFragment, Observer {
            val eventResult = it ?: return@Observer
            if (eventResult.success != null) {
                typeEventAdapter = TypeEventAdapter(requireContext(),eventResult.success) //{ eventType -> onClickListener(eventType)}
                _binding?.eventCategoryIdId?.setAdapter(typeEventAdapter)
            }
        })


        viewModel.eventReportResult.observe( this@CreateAlertFragment, Observer {
            val eventReportResultado = it ?: return@Observer
            if(eventReportResultado.success!=null){
                _binding?.loader.apply { myLoader.visibility = View.GONE }
                showMessage("Alerta enviada con éxito")
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            if (eventReportResultado.errors !=null){
                eventReportResultado.errors.forEach {
                    showMessage("$it")
                }
            }


        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.apply {
            textTooblar.text = "Creando alerta"
            toolbar.btn_Back.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        _binding?.eventTitle?.text = args.eventName
        _binding?.eventGeneralDescription?.text = args.eventDescription

        _binding?.afectationsRangeId?.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            _range = rangeAdapter?.getItem(position)
        }

        _binding?.eventCategoryIdId?.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            _eventType = typeEventAdapter?.getItem(position)
        }

        _binding?.btnTakePhoto?.setOnClickListener{
            checkPermissionCamera()
        }

        _binding?.btnSelectFoto?.setOnClickListener {
            checkPermissionFile()
        }

        _binding?.btnSelectImage?.setOnClickListener{
            if(!clicked!!){
                showDialog("Atención",getString(R.string.message_take_photo))
            }else{
                runAnimations()
            }
        }

        _binding?.btnOpenMaps?.setOnClickListener{
            val intent = Intent(requireContext(),MapsActivity::class.java)
            setMapsValue.launch(intent)

        }

        _binding?.eventDate?.setOnClickListener {
            showDatePickerDialog()
        }
        _binding?.btnSaveAlert?.setOnClickListener{

            if(validateForm()) {
                _binding?.loader.apply { myLoader.visibility = View.VISIBLE }

                val newAlert = CreateAlertRequest(
                    eventDescription =  _binding?.eventDescription?.text.toString(),
                    eventDate = _binding?.eventDate?.text.toString(),
                    eventPlace = _binding?.eventPlace?.text.toString(),
                    eventAditionalInformation = _binding?.eventAditionalInformation?.text.toString(),
                    affectedPeople = _binding?.affectedPeople?.isChecked,
                    affectedFamily = _binding?.affectedFamily?.isChecked,
                    affectedAnimals = _binding?.affectedAnimals?.isChecked,
                    affectedInfrastructure = _binding?.affectedInfrastructure?.isChecked,
                    affectedLivelihoods = _binding?.affectedLivelihoods?.isChecked,
                    affectedEnviroment = _binding?.affectedEnviroment?.isChecked,
                    eventTypeId = _eventType?.id,
                    townId = args.townId,
                    statusId = 16,
                    afectationsRangeId = _range?.id,
                    latitude = latitude,
                    longitude = longitude
                )
                System.out.println(image)
               viewModel.store(newAlert,image)
            }
        }
    }

    private fun setClickable() {
        if(!clicked!!){
            _binding?.btnTakePhoto?.isClickable = true
            _binding?.btnSelectFoto?.isClickable = true
        }else{
            _binding?.btnTakePhoto?.isClickable = false
            _binding?.btnSelectFoto?.isClickable = false
        }
    }

    private fun setAnimation() {
        if(!clicked!!){
            _binding?.btnTakePhoto?.startAnimation(fromBottom)
            _binding?.btnSelectFoto?.startAnimation(fromBottom)
            _binding?.btnSelectImage?.startAnimation(rotateOpen)
            _binding?.btnSelectImage?.setIconResource(R.drawable.ic_baseline_close_24)

        }else{
            _binding?.btnTakePhoto?.startAnimation(toBottom)
            _binding?.btnSelectFoto?.startAnimation(toBottom)
            _binding?.btnSelectImage?.startAnimation(rotateClose)
            _binding?.btnSelectImage?.setIconResource(R.drawable.ic_baseline_monochrome_photos_24)
        }
    }

    private fun setVisibility() {
       if(!clicked!!){
         _binding?.btnTakePhoto?.visibility = View.VISIBLE
           _binding?.btnSelectFoto?.visibility = View.VISIBLE
       }else{
           _binding?.btnTakePhoto?.visibility = View.GONE
           _binding?.btnSelectFoto?.visibility = View.GONE
       }
    }

    private fun showMessage(msg:String) {
        Toast.makeText(requireContext(),"$msg", Toast.LENGTH_LONG).show()

    }


    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                 _binding?.eventPhoto?.setImageURI(uri)
                runAnimations()
            }
        }
    }

    private val takeFileResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { isSuccess ->
        if (isSuccess.resultCode == Activity.RESULT_OK) {
            val uri  = isSuccess.data?.data
            currentPhotoPath = uri?.let { RealPathUtil.getRealPath(requireContext(), it) }
           val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            image = File(currentPhotoPath)
            _binding?.eventPhoto?.setImageBitmap(bitmap)
            runAnimations()
        }
    }


    private val setMapsValue = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == RESULT_MAP) {
            val adrress = activityResult.data?.getStringExtra(MapViewFragment.ADDRESS)
            latitude = activityResult.data?.getDoubleExtra(MapViewFragment.LATITUDE,0.0)
            longitude = activityResult.data?.getDoubleExtra(MapViewFragment.LONGITUDE,0.0)
            _binding?.eventPlace?.setText("$adrress")
        }else{

        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val storageDir: File? =  requireContext().cacheDir
        val tmpFile = File.createTempFile("tmp_image_file", ".png", storageDir).apply {
            createNewFile()
            deleteOnExit()
           currentPhotoPath = absolutePath

        }
        image = tmpFile
        return FileProvider.getUriForFile(requireContext(), "com.alercom.app.fileprovider", tmpFile)
    }

    private fun checkPermissionCamera(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                takeImage()
            }else{
                requireActivity().requestPermissions(
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), REQUEST_PERMISSION_CAMERA
                )
            }
        }else{
            takeImage()

        }
    }

    private fun checkPermissionFile(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                getFile()
            }else{
                requireActivity().requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), REQUEST_PERMISSION_FILE
                )
            }
        }else{
            getFile()

        }
    }

    private fun getFile() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                latestTmpUri = uri
                takeFileResult.launch(intent)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        System.out.println(requestCode)
        if(requestCode == REQUEST_PERMISSION_CAMERA){
            if(permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takeImage()
            }
        }

        if(requestCode == REQUEST_PERMISSION_FILE){
            if(permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getFile()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun validateForm(): Boolean {

        if(_binding?.eventCategoryIdId?.text!!.isEmpty()){
            _binding?.lAlertCategory?.error = "La tipo de evento no puede estar vacio"
            return false
        }else{
            _binding?.lAlertCategory?.error = null

        }

        if(_binding?.eventDescription?.text!!.isEmpty()){
            _binding?.lEventDescription?.error = "La descripción no puede estar vacia"
            return false
        }else{
            _binding?.lEventDescription?.error = null

        }

        if(_binding?.eventDescription?.text!!.length < 10){
            _binding?.lEventDescription?.error = "La descripción no puede tener menos de 10 carácteres"
            return false
        }else{
            _binding?.lEventDescription?.error = null

        }


        if(_binding?.eventDate?.text!!.isEmpty()){
            _binding?.lEventDate?.error ="La fecha del evento no puede estar vacia"
            return false
        }else{
            _binding?.lEventDate?.error = null
        }

        if(_binding?.eventPlace?.text!!.isEmpty()){
            _binding?.lEventPlace?.error ="El lugar no puede estar vacio"
            return false
        }else{
            _binding?.lEventPlace?.error = null
        }


        if(_binding?.afectationsRangeId?.text!!.isEmpty()){
            _binding?.lAfectationsRangeId?.error = "El rango de afectaciones no puede estar vacio"
            return false
        }else{
            _binding?.lAfectationsRangeId?.error = null
        }

        return true
    }
    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment{ day, month, year -> onDateSelect(day,month,year)}
        datePicker.show(parentFragmentManager,"DatePicker")
    }
    private fun onDateSelect(day:Int,month: Int,year:Int){
        val month = month + 1
        _binding?.eventDate?.setText("$year-$month-$day")
    }

    private fun showDialog(title:String,msg:String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("$title")
        builder.setMessage(msg)
        builder.setPositiveButton("Entendido", DialogInterface.OnClickListener() {
                dialogInterface: DialogInterface, i: Int ->
            runAnimations()
        })
        builder.setOnCancelListener{
            clicked=true
            runAnimations()
        }

        builder.show()

    }

    private fun runAnimations(){
        setAnimation()
        setVisibility()
        setClickable()
        clicked=!clicked!!
    }

    /*
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =  requireContext().filesDir

       val file = File.createTempFile(
           "JPEG_${timeStamp}_", /* prefix */
           ".jpg", /* suffix */
           storageDir /* directory */
       ).apply {
           // Save a file: path for use with ACTION_VIEW intents
           currentPhotoPath = absolutePath
       }
        image = file
        System.out.println("Aqui file $file $currentPhotoPath")
        return file
    }
*/

/*
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {

                    null
                }
                // Continue only if the File was successfully created
               photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.alercom.app.fileprovider",
                        it
                    )
                    System.out.println(photoURI)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                  // startForResult.launch(Intent(requireContext(), CreateAlertFragment::class.java))
                   //registerForActivityResult()
                   // startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }


            }
        }
    }*/
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        System.out.println("Por aqui $currentPhotoPath")
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            System.out.println("adsd  ${image?.absolutePath}")
            imageBitmap = BitmapFactory.decodeFile(image?.path)
            _binding?.eventPhoto?.setImageBitmap(imageBitmap)

        }
    }
*/

}