package com.alercom.app.ui.alerts.edit

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alercom.app.MainActivity
import com.alercom.app.R
import com.alercom.app.adapter.TypeEventAdapter
import com.alercom.app.data.model.Alert
import com.alercom.app.data.model.EventType
import com.alercom.app.data.model.Reference
import com.alercom.app.databinding.EditAlertFragmentBinding
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.resources.DatePickerFragment
import com.alercom.app.resources.MapViewFragment
import com.alercom.app.resources.MapsActivity
import com.alercom.app.resources.RealPathUtil
import com.app.alercom.adapter.AffectsRangeSpinnerAdapter
import com.squareup.picasso.Picasso
import java.io.File

class EditAlertFragment : Fragment() {

    companion object {
        fun newInstance() = EditAlertFragment()
    }


    private lateinit var viewModel: EditAlertViewModel
    private var _binding: EditAlertFragmentBinding?  = null
    private val binding get() = _binding!!
    private val args: EditAlertFragmentArgs by navArgs()
    private var rangeAdapter :  AffectsRangeSpinnerAdapter? = null
    private var _range : Reference? = null
    private var _eventType: EventType? = null
    private var typeEventAdapter :  TypeEventAdapter? = null
    private var image :File? = null
    private var currentPhotoPath: String? = null
    private var latestTmpUri : Uri? = null
    private val REQUEST_PERMISSION_CAMERA = 100
    private var alert : Alert? = null
    private val RESULT_MAP = 2
    private var latitude :Double? = null
    private var longitude :Double? = null
    private val REQUEST_PERMISSION_FILE = 200
    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(requireContext(),R.anim.to_bottom_amin) }
    private var clicked : Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditAlertFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, EditAlertViewModelFactory())
            .get(EditAlertViewModel::class.java)

        viewModel.rangesResult.observe(this@EditAlertFragment, Observer {
            val eventResult = it ?: return@Observer
            if (eventResult.success != null) {
                rangeAdapter = AffectsRangeSpinnerAdapter(requireContext(),eventResult.success) //{ eventType -> onClickListener(eventType)}
                _binding?.afectationsRangeId?.setAdapter(rangeAdapter)
                viewModel.edit(args.alertId)
            }
        })
        viewModel.eventTypeResult.observe(this@EditAlertFragment, Observer {
            val eventResult = it ?: return@Observer
            if (eventResult.success != null) {
                typeEventAdapter = TypeEventAdapter(requireContext(),eventResult.success) //{ eventType -> onClickListener(eventType)}
                _binding?.eventCategoryIdId?.setAdapter(typeEventAdapter)
            }
        })

        viewModel.eventResult.observe(this@EditAlertFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                 alert = eventResult.success
                _binding?.eventTitle?.text = alert?.eventType?.category?.referenceName
                _binding?.eventGeneralDescription?.text = alert?.eventType?.category?.referenceDescription
                _binding?.eventDescription?.setText(alert?.eventDescription)
                _binding?.eventDate?.setText(alert?.eventDate)
                _binding?.eventPlace?.setText(alert?.eventPlace)
                latitude =  if(alert?.latitude != "null"){
                                alert?.latitude?.toDouble()
                            }else{
                                0.0
                             }

                longitude = if(alert?.longitude != "null"){
                    alert?.longitude?.toDouble()
                }else{
                    0.0
                }

                when(alert?.affectedPeople?.toString()){
                    "1" -> _binding?.affectedPeople?.isChecked = true
                    "0" -> _binding?.affectedPeople?.isChecked = false
                }
                when(alert?.affectedAnimals?.toString()){
                    "1" -> _binding?.affectedAnimals?.isChecked = true
                    "0" -> _binding?.affectedAnimals?.isChecked = false
                }
                when(alert?.affectedInfrastructure?.toString()){
                    "1" -> _binding?.affectedInfrastructure?.isChecked = true
                    "0" -> _binding?.affectedInfrastructure?.isChecked = false
                }
                when(alert?.affectedLivelihoods?.toString()){
                    "1" -> _binding?.affectedLivelihoods?.isChecked = true
                    "0" -> _binding?.affectedLivelihoods?.isChecked = false
                }
                when(alert?.affectedFamily?.toString()){
                    "1" -> _binding?.affectedFamily?.isChecked = true
                    "0" -> _binding?.affectedFamily?.isChecked = false
                }
                when(alert?.affectedEnviroment?.toString()){
                    "1" -> _binding?.affectedEnviroment?.isChecked = true
                    "0" -> _binding?.affectedEnviroment?.isChecked = false
                }
                _binding?.eventAditionalInformation?.setText(alert?.eventAditionalInformation)
                _binding?.afectationsRangeId?.setText(alert?.affectationRange?.referenceName.toString()!!,false)
                _binding?.eventCategoryIdId?.setText(alert?.eventType?.eventTypeName.toString()!!,false)
                _range = alert?.affectationRange
                _eventType = alert?.eventType
                if(alert?.files?.size!! > 0){
                    Picasso.with(context)
                        .load(alert?.files?.get(0)?.realPath)
                        //.resize(30, 20)
                       // .centerCrop()
                        .into(_binding?.eventPhoto)
                }else{
                    Picasso.with(context).load(R.drawable.no_photo).into(_binding?.eventPhoto);
                }
                _binding?.loader?.myLoader?.visibility = View.GONE
                _binding?.btnSelectImage?.visibility = View.VISIBLE
                _binding?.btnOpenMaps?.visibility = View.VISIBLE

                _binding?.toolbar?.btnDelete?.visibility = View.VISIBLE
                if(alert?.statusId != 11){
                    _binding?.btnUpdateAlert?.visibility = View.GONE
                    _binding?.toolbar?.btnDelete?.visibility = View.GONE
                }
            }
        })

        viewModel.alertUpdateResult.observe(this@EditAlertFragment, Observer {
            val alertUpdateResult = it ?: return@Observer
            if (alertUpdateResult.success != null) {
                showMessage("Alerta actualizada con éxito")
                findNavController().navigateUp()
            }
        })

        viewModel.deleteAlertResult.observe(this@EditAlertFragment, Observer {
            val alertUpdateResult = it ?: return@Observer
            if (alertUpdateResult.success != null) {
                showMessage("Alerta eliminada con éxito")
                findNavController().navigateUp()
            }
        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.apply {
            textTooblar.text = "Actualizando alerta"
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }

         _binding?.loader?.myLoader?.visibility = View.VISIBLE
        _binding?.btnSelectImage?.visibility = View.GONE
        _binding?.btnOpenMaps?.visibility = View.GONE
        viewModel.getAffectsRange()
        viewModel.getEventTypes(args.categoryId)

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
        _binding?.eventDate?.setOnClickListener {
            showDatePickerDialog()
        }

        _binding?.btnOpenMaps?.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("latitude",latitude)
            intent.putExtra("longitude",longitude)
            intent.putExtra("address",alert?.eventPlace)
            intent.putExtra("view","edit")
            setMapsValue.launch(intent)

        }

        _binding?.btnUpdateAlert?.setOnClickListener{

            if(validateForm()) {
                _binding?.toolbar?.btnDelete?.visibility = View.GONE
                _binding?.loader?.myLoader?.visibility = View.VISIBLE
                _binding?.btnSelectImage?.visibility = View.GONE
                _binding?.btnOpenMaps?.visibility = View.GONE
                _binding?.btnSelectFoto?.visibility = View.GONE
                _binding?.btnTakePhoto?.visibility = View.GONE

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
                    townId = alert?.townId,
                    statusId = alert?.statusId,
                    afectationsRangeId = _range?.id,
                    latitude = latitude,
                    longitude = longitude
                )
                viewModel.update(alert?.id!!,newAlert,image)

            }
        }

        _binding?.toolbar?.btnDelete?.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("¡Atención!")
            builder.setMessage("¿Está segura/o de eliminar la alerta?")
                .setNegativeButton("No, cancelar",DialogInterface.OnClickListener(){
                        dialogInterface: DialogInterface, i: Int ->

                })
                .setPositiveButton("Si, eliminar", DialogInterface.OnClickListener() {
                        dialogInterface: DialogInterface, i: Int ->
                    _binding?.loader?.apply { myLoader.visibility = View.VISIBLE }
                    viewModel.delete(alert?.id!!)
                })
                .show()

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

    private fun getNavController(): NavController?{
        return  (activity as? MainActivity)?.navController
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

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                _binding?.eventPhoto?.setImageURI(uri)
                runAnimations()
            }
        }
    }

    private val setMapsValue = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == RESULT_MAP) {
            val address = activityResult.data?.getStringExtra(MapViewFragment.ADDRESS)
            latitude = activityResult.data?.getDoubleExtra(MapViewFragment.LATITUDE,0.0)
            longitude = activityResult.data?.getDoubleExtra(MapViewFragment.LONGITUDE,0.0)
            _binding?.eventPlace?.setText("$address")

        }else{

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
            if(ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                takeImage()
             }else{

                requestPermissions(
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

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_PERMISSION_CAMERA){
            if(permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //dispatchTakePictureIntent()
                takeImage()
                System.out.println("Por RequestPermission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun validateForm(): Boolean {
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

    private fun showMessage(msg:String) {
        Toast.makeText(requireContext(),"$msg", Toast.LENGTH_LONG).show()

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

}