package com.alercom.app.resources

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.alercom.app.R
import com.alercom.app.databinding.CreateAlertFragmentBinding

import com.google.android.gms.common.api.Status

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class MapsFragment : Fragment(),OnMapReadyCallback {
    /*
 private lateinit var map : GoogleMap
    companion object {
        private const val REQUEST_CODE_LOCATION = 0
        private const val AUTOCOMPLETE_REQUEST_CODE = 1
    }
   /* private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(1.2214781,-77.2816517)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in zION"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(sydney,20f),4000,null
        )
    }*/
   private var _binding: FragmentMapsBinding?  = null

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
       // createMarquer()
        enableLocation()

        map.setOnMapLongClickListener {
            val markerOptions : MarkerOptions = MarkerOptions().position(it)
            map.addMarker(markerOptions)
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(it,17f),4000,null
            )
        }

        _binding?.btnAsignar?.setOnClickListener {

            System.out.println("Hp correoQ")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //_binding?.mySearcDirection?.setText("Wiiisi")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Places.initialize(requireContext(),getString(R.string.google_api_key))
        isLocationPermissionGranted()

    }


   private fun isLocationPermissionGranted() : Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                enableLocation()
            }
        }else{
            enableLocation()
        }
       return false
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
                 requestLocationPermission()
             }


     }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
        )){
            Toast.makeText(requireContext(),"Ve ajustes",Toast.LENGTH_LONG).show()
        }else{
            System.out.println("Si pide perposjksdfkg")
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,

                ), REQUEST_CODE_LOCATION
            )
         /*   ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION),
                   REQUEST_CODE_LOCATION
            )*/
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       when(requestCode){
           REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
               if (ActivityCompat.checkSelfPermission(
                       requireContext(),
                       Manifest.permission.ACCESS_FINE_LOCATION
                   ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                       requireContext(),
                       Manifest.permission.ACCESS_COARSE_LOCATION
                   ) == PackageManager.PERMISSION_GRANTED
               ) {
                  map.isMyLocationEnabled = true
               }

           }else{
               Toast.makeText(requireContext(),"Ve ajustes y acepta",Toast.LENGTH_LONG).show()
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

                Toast.makeText(requireContext(),"Activa los permisos en ajustes",Toast.LENGTH_LONG).show()
            }


    }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Places.initialize(requireContext(),getString(R.string.google_api_key))

        createMapFragment()

      val autocompleteFragment = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
              as AutocompleteSupportFragment

      autocompleteFragment.setPlaceFields(listOf(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG))

      // Set up a PlaceSelectionListener to handle the response.
      autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
          override fun onPlaceSelected(place: Place) {
              System.out.println("Place: ${place.name}, ${place.latLng}")
              createMarquer(place.latLng)

          }

          override fun onError(status: Status) {
              System.out.println("Error $status")
          }
      })




        _binding?.btnAsignar?.setOnClickListener {
            System.out.println("Sis aadadsdasd")
            System.out.println(map.cameraPosition)
        }


    }

    private  fun createMapFragment(){

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private  fun createMarquer(latLng: LatLng) {
        //val location :Location = map.myLocation
        val sydney = LatLng(latLng.latitude,latLng.longitude)
        map.addMarker(MarkerOptions().position(sydney).title("Marker in Disabled"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
       // map.setLocationSource()
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(sydney,17f),4000,null
        )

    }

*/
    override fun onMapReady(p0: GoogleMap) {

    }

}