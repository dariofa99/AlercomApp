package com.alercom.app.ui.alerts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alercom.app.MainActivity
import com.alercom.app.data.model.Reference
import com.alercom.app.databinding.CreateAlertFragmentBinding
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.resources.DatePickerFragment
import com.app.alercom.adapter.AffectsRangeSpinnerAdapter
import java.io.File


class CreateAlertFragment  : Fragment() {

    companion object {
        fun newInstance() = CreateAlertFragment()
    }
    private val args: CreateAlertFragmentArgs by navArgs()
    private lateinit var viewModel: CreateAlertViewModel
    private var rangeAdapter :  AffectsRangeSpinnerAdapter? = null
    private var _binding: CreateAlertFragmentBinding?  = null
    private val binding get() = _binding!!
    private var _range : Reference? = null
    private var image :File? = null
    private var currentPhotoPath: String? = null
    var latestTmpUri : Uri? = null
    private val CARPETA_PRINCIPAL : String? = "myImagesApp/"
    private val CARPETA_IMAGEN : String? = "imagenes"
    private val RUTA_IMAGEN : String? = CARPETA_PRINCIPAL + CARPETA_IMAGEN
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PERMISSION_CAMERA = 100
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = this.requireActivity().actionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = CreateAlertFragmentBinding.inflate(
            inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, CreateAlertViewModelFactory())
            .get(CreateAlertViewModel::class.java)
        image = null
       viewModel.getAffectsRange()

        viewModel.rangesResult.observe(this@CreateAlertFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                rangeAdapter = AffectsRangeSpinnerAdapter(requireContext(),eventResult.success) //{ eventType -> onClickListener(eventType)}
                _binding?.afectationsRangeId?.setAdapter(rangeAdapter)
            }
        })
        viewModel.eventReportResult.observe( this@CreateAlertFragment, Observer {
            val eventReportResultado = it ?: return@Observer
         //   _binding?.myLoader?.visibility = View.GONE
           // _binding?.loading?.visibility = View.GONE
            if(eventReportResultado.success!=null){
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

        _binding?.eventTitle?.text = args.eventName
        _binding?.eventGeneralDescription?.text = args.eventDescription
        _binding?.afectationsRangeId?.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            _range = rangeAdapter?.getItem(position)
        }
        _binding?.btnTakePhoto?.setOnClickListener{
            checkPermissionCamera()
        }
        _binding?.eventDate?.setOnClickListener {
            showDatePickerDialog()
        }
        _binding?.btnSaveAlert?.setOnClickListener{

            if(validateForm()) {
             //  _binding?.myLoader?.visibility = View.VISIBLE
             //   _binding?.loading?.visibility = View.VISIBLE

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
                    eventTypeId = args.eventTypeId,
                    townId = args.townId,
                    statusId = 16,
                    afectationsRangeId = _range?.id,
                    imageCompressed = "fsdf"
                )
               viewModel.store(newAlert,image)
            }
        }
    }

    private fun showMessage(msg:String) {
        Toast.makeText(requireContext(),"$msg", Toast.LENGTH_LONG).show()

    }


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

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                 _binding?.eventPhoto?.setImageURI(uri)

            }
        }
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
                //dispatchTakePictureIntent()
             /*   if(ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    if(ActivityCompat.checkSelfPermission(requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        dispatchTakePictureIntent()
                        System.out.println("Por check")
                    }

                }
*/


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
           // dispatchTakePictureIntent()
            System.out.println("Por vewrsions")

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
}