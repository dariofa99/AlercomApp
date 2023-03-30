package com.alercom.app.ui.alerts.show

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alercom.app.R
import com.alercom.app.adapter.TypeEventAdapter
import com.alercom.app.data.model.Alert
import com.alercom.app.data.model.EventType
import com.alercom.app.data.model.Reference

import com.alercom.app.databinding.ShowEventFragmentBinding
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.resources.MapViewFragment
import com.alercom.app.resources.MapsActivity
import com.alercom.app.ui.alerts.edit.EditAlertFragmentArgs

import com.app.alercom.adapter.AffectsRangeSpinnerAdapter
import com.squareup.picasso.Picasso
import java.io.File

class ShowEventFragment : Fragment() {

    companion object {
        fun newInstance() = ShowEventFragment()
    }

    private var _binding: ShowEventFragmentBinding?  = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShowEventViewModel

    private val args: EditAlertFragmentArgs by navArgs()
    private var rangeAdapter :  AffectsRangeSpinnerAdapter? = null
    private var _range : Reference? = null
    private var _eventType: EventType? = null
    private var typeEventAdapter :  TypeEventAdapter? = null
    private var image : File? = null
    private var currentPhotoPath: String? = null
    var latestTmpUri : Uri? = null
    private val REQUEST_PERMISSION_CAMERA = 100
    private var alert : Alert? = null
    private val RESULT_MAP = 2
    private var latitude :Double? = null
    private var longitude :Double? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ShowEventFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.edit(args.alertId)
        _binding?.toolbar?.apply {
            textTooblar.text = "Actualizando alerta"
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        _binding?.btnOpenMaps?.setOnClickListener{
            val intent = Intent(requireContext(), MapsActivity::class.java)
            intent.putExtra("latitude",latitude)
            intent.putExtra("longitude",longitude)
            intent.putExtra("address",alert?.eventPlace)
            intent.putExtra("view","show")
            setMapsValue.launch(intent)
        }

        _binding?.btnAcceptEvent?.setOnClickListener {

            //showDialog("Atención!","Esta seguro de aceptar la alerta")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("¡Atención!")
            builder.setMessage("¿Está segura/o de aceptar la alerta?")
                .setNegativeButton("No, cancelar",DialogInterface.OnClickListener(){
                        dialogInterface: DialogInterface, i: Int ->

                })
                .setPositiveButton("Si, aceptar", DialogInterface.OnClickListener() {
                        dialogInterface: DialogInterface, i: Int ->
                    _binding?.loader?.apply { myLoader.visibility = View.VISIBLE }
                    val newAlert = fillAlert(13)
                    viewModel.update(alert?.id!!,newAlert,image)
                })
               .show()


        }

        _binding?.btnCancelEvent?.setOnClickListener {

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("¡Atención!")
            builder.setMessage("¿Está segura/o de denegar la alerta?")
                .setNegativeButton("No, cancelar",DialogInterface.OnClickListener(){
                        dialogInterface: DialogInterface, i: Int ->

                })
                .setPositiveButton("Si, denegar", DialogInterface.OnClickListener() {
                        dialogInterface: DialogInterface, i: Int ->
                    _binding?.loader?.apply { myLoader.visibility = View.VISIBLE }
                    val newAlert = fillAlert(12)
                    viewModel.update(alert?.id!!,newAlert,image)
                })
                .show()

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ShowEventViewModelFactory())
            .get(ShowEventViewModel::class.java)

        viewModel.eventResult.observe(this@ShowEventFragment, Observer {
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
                if(alert?.files?.size!! > 0){
                    Picasso.with(context)
                        .load(alert?.files?.get(0)?.realPath)
                        .into(_binding?.eventPhoto)
                }else{
                    Picasso.with(context).load(R.drawable.no_photo).into(_binding?.eventPhoto);
                }
                if(alert?.statusId != 11){
                    _binding?.btnAcceptEvent?.visibility = View.GONE
                    _binding?.btnCancelEvent?.visibility = View.GONE
                }
                _binding?.loader?.apply { myLoader.visibility = View.GONE }
            }
        })

        viewModel.alertUpdateResult.observe(this@ShowEventFragment, Observer {
            val alertUpdateResult = it ?: return@Observer
            if (alertUpdateResult.success != null) {
                _binding?.loader?.apply { myLoader.visibility = View.GONE }
                showMessage("Alerta actualizada con éxito")
                findNavController().navigateUp()
            }
        })
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


    private fun showMessage(msg:String) {
        Toast.makeText(requireContext(),"$msg", Toast.LENGTH_LONG).show()

    }

    private fun fillAlert(status:Int): CreateAlertRequest {

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
            eventTypeId = _eventType?.id,
            townId = alert?.townId,
            statusId = status,
            afectationsRangeId = _range?.id,
            latitude = latitude,
            longitude = longitude
        )
        return newAlert
    }

    private fun showDialog(title:String,msg:String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("$title")
        builder.setMessage(msg)
            .setPositiveButton("Entendido", DialogInterface.OnClickListener() {
                    dialogInterface: DialogInterface, i: Int ->
                //Dario   Toast.makeText(requireContext(),"Entendido",Toast.LENGTH_LONG).show()

            }).show()

    }

}