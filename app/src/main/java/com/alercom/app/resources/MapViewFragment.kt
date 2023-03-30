package com.alercom.app.resources

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.alercom.app.R
import com.alercom.app.databinding.FragmentMapViewBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class MapViewFragment : Fragment(),OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    companion object {
        private const val AUTOCOMPLETE_REQUEST_CODE = 2
        private const val REQUEST_CODE_LOCATION = 0
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"
        const val ADDRESS = "ADDRESS"
        fun newInstance() =
            MapViewFragment()

    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map : GoogleMap
    var _binding: FragmentMapViewBinding? = null
    private var latLng : LatLng? = null
    private var address : String? = null
    private val binding get() = _binding!!
    private var endMarquer : Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        Places.initialize(requireContext(),getString(R.string.google_api_key))
        _binding?.btnAsignar?.setOnClickListener {
            val intent = Intent()
            System.out.println(latLng?.latitude)
            System.out.println(latLng?.longitude)
            System.out.println("**********************************************************")
            intent.putExtra(LATITUDE,latLng?.latitude)
            intent.putExtra(LONGITUDE,latLng?.longitude)
            intent.putExtra(ADDRESS,address)
            activity?.setResult(AUTOCOMPLETE_REQUEST_CODE,intent)
            activity?.finish()

        }
        createMapFragment()
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
                as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                System.out.println("Place: ${place.name}, ${place.latLng}")
                latLng = place.latLng
                address = getAddress(latLng!!)
                val newMarquer = MarkerOptions().position(latLng!!).title("$address")
                createMarquer(newMarquer)

            }

            override fun onError(status: Status) {
                System.out.println("Error $status")
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapViewBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onMapReady(googleMap: GoogleMap) {

        val b : Bundle? = requireActivity().intent.extras
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMyLocationButtonClickListener(this)
        enableLocation()


        map.setOnMapClickListener() {
            address = getAddress(it)
            latLng = it
            val newMarquer = MarkerOptions().position(it).title("$address")
            createMarquer(newMarquer)
        }
        if(b!=null && b.getString("view").toString()=="show"){
            _binding?.btnAsignar?.visibility = View.GONE
            _binding?.contentButtos?.visibility = View.GONE
        }

        if(b!=null && b.getDouble("latitude")!=0.0 && b.getDouble("longitude")!=0.0){
            latLng = LatLng(b.getDouble("latitude"), b.getDouble("longitude"))
            address = b.getString("address")
             val newMarquer = MarkerOptions().position(latLng!!).title("$address")
              createMarquer(newMarquer)
        }else{

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        latLng = LatLng(location?.latitude!!, location?.longitude!!)
                        address = getAddress(latLng!!)
                        val newMarquer = MarkerOptions().position(latLng!!).title("$address")
                        createMarquer(newMarquer)
                    }
            }


        }




    }

    private  fun createMapFragment(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    fun removeMarquer(){
        if(endMarquer!=null) endMarquer?.remove()
    }

    private  fun createMarquer(newMarquer: MarkerOptions) {
        removeMarquer()
        newMarquer.icon(
            BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_VIOLET
            )
        )
        endMarquer =  map.addMarker(newMarquer)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(newMarquer.position,17f),4000,null
        )
    }

    fun getAddress(latLng: LatLng) : String{
        val geocoder = Geocoder(requireContext())
        val address : List<Address>?
        var firstAdress : Address
        var addressText = ""
        try {
            address = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1)
            if(address!=null && address.isNotEmpty()){
                firstAdress = address[0]
                if(firstAdress.maxAddressLineIndex > 0){
                    for (i in 0 .. firstAdress.maxAddressLineIndex){
                        addressText += firstAdress.getAddressLine(i) + "\n"
                    }
                }else{
                    addressText += firstAdress.thoroughfare +", "+ firstAdress.subThoroughfare
                }
            }
            return addressText
        }catch (e:Exception){
            return "Dirección no encontrada"
        }

    }

    private var locationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
            if(isGranted){
                val b : Bundle? = requireActivity().intent.extras
                if(b!=null && b.getDouble("latitude")!=0.0 && b.getDouble("longitude")!=0.0){
                    latLng = LatLng(b.getDouble("latitude"), b.getDouble("longitude"))
                    address = b.getString("address")
                    val newMarquer = MarkerOptions().position(latLng!!).title("$address")
                    createMarquer(newMarquer)
                }else{
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location : Location? ->
                            latLng = LatLng(location?.latitude!!, location?.longitude!!)
                            address = getAddress(latLng!!)
                            val newMarquer = MarkerOptions().position(latLng!!).title("$address")
                            createMarquer(newMarquer)
                        }
                }


                map.isMyLocationEnabled = true
            }else{

            }

    }

    private fun enableLocation(){
        if(!::map.isInitialized) return
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }else{
            locationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }


    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )){
            Toast.makeText(requireContext(),"Ve ajustes y activa los permisos", Toast.LENGTH_LONG).show()
        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                    ), REQUEST_CODE_LOCATION
            )

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){

                    map.isMyLocationEnabled = true



            }else{
                Toast.makeText(requireContext(),"Ve ajustes y acepta los permisos", Toast.LENGTH_LONG).show()
            }else -> {}
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


    }

    override fun onResume() {
        super.onResume()
        if(!::map.isInitialized) return
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = false
            Toast.makeText(requireContext(),"Activa los permisos en ajustes", Toast.LENGTH_LONG).show()
        }


    }

    override fun onMyLocationButtonClick(): Boolean {



        return false
    }

}