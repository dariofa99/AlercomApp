package com.alercom.app.ui.alerts.edit

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alercom.app.MainActivity
import com.alercom.app.R
import com.alercom.app.adapter.EventTypeAdapter
import com.alercom.app.data.model.Reference
import com.alercom.app.databinding.EditAlertFragmentBinding
import com.alercom.app.databinding.EventFragmentBinding
import com.alercom.app.resources.DatePickerFragment
import com.alercom.app.ui.alerts.CreateAlertFragmentArgs
import com.alercom.app.ui.alerts.events.EventViewModel
import com.alercom.app.ui.alerts.events.EventViewModelFactory
import com.app.alercom.adapter.AffectsRangeSpinnerAdapter
import com.squareup.picasso.Picasso
import java.io.File

class EditAlertFragment : Fragment() {

    companion object {
        fun newInstance() = EditAlertFragment()
    }

    private val args: EditAlertFragmentArgs by navArgs()
    private var rangeAdapter :  AffectsRangeSpinnerAdapter? = null
    private lateinit var viewModel: EditAlertViewModel
    private var _binding: EditAlertFragmentBinding?  = null
    private val binding get() = _binding!!
    private var _range : Reference? = null
    private var image :File? = null
    private var currentPhotoPath: String? = null
    var latestTmpUri : Uri? = null
    private val REQUEST_PERMISSION_CAMERA = 100

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
                viewModel.index(args.alertId)
            }
        })

        viewModel.eventResult.observe(this@EditAlertFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                _binding?.eventTitle?.text = eventResult.success.eventType?.eventTypeName
                _binding?.eventGeneralDescription?.text = eventResult.success.eventType?.eventTypeDescription
                _binding?.eventDescription?.setText(eventResult.success.eventDescription)
                _binding?.eventDate?.setText(eventResult.success.eventDate)
                _binding?.eventPlace?.setText(eventResult.success.eventPlace)
                _binding?.affectedFamily?.isChecked = eventResult.success.affectedFamily?.toString().toBoolean()
                _binding?.affectedAnimals?.isChecked = eventResult.success.affectedAnimals?.toString().toBoolean()
                _binding?.affectedInfrastructure?.isChecked = eventResult.success.affectedInfrastructure?.toString().toBoolean()
                _binding?.affectedLivelihoods?.isChecked = eventResult.success.affectedLivelihoods?.toString().toBoolean()
                _binding?.affectedPeople?.isChecked =  eventResult.success.affectedPeople?.toString().toBoolean()
                _binding?.eventAditionalInformation?.setText(eventResult.success?.eventAditionalInformation)
                _binding?.afectationsRangeId?.setText(eventResult.success.affectationRange?.referenceName.toString()!!,false)

                if(eventResult.success.files?.size!! > 0){
                    Picasso.with(context)
                        .load(eventResult.success.files?.get(0)?.realPath)
                        .resize(100, 100)
                        .centerCrop()
                        .into(_binding?.eventPhoto)
                }

            }


        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAffectsRange()

        _binding?.afectationsRangeId?.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            System.out.println(position)
            _range = rangeAdapter?.getItem(position)
        }
        _binding?.btnTakePhoto?.setOnClickListener{
            checkPermissionCamera()
        }
        _binding?.eventDate?.setOnClickListener {
            showDatePickerDialog()
        }

    }

    private fun getNavController(): NavController?{
        return  (activity as? MainActivity)?.navController
    }

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            latestTmpUri?.let { uri ->
                _binding?.eventPhoto?.setImageURI(uri)

            }
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