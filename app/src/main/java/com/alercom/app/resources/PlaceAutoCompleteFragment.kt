package com.alercom.app.resources

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alercom.app.R
import com.alercom.app.databinding.CreateAlertFragmentBinding
import com.alercom.app.databinding.FragmentPlaceAutoCompleteBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class PlaceAutoCompleteFragment : Fragment() {

    private var _binding: FragmentPlaceAutoCompleteBinding?  = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaceAutoCompleteBinding.inflate(
            inflater, container, false)

        return binding.root
    }

    companion object {

        fun newInstance() = PlaceAutoCompleteFragment()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  Places.initialize(requireContext(),getString(R.string.google_api_key))
      /*  val autocompleteFragment = childFragmentManager.findFragmentById(R.id.place_autocomplete_fragment)
                as AutocompleteSupportFragment

        autocompleteFragment.setPlaceFields(listOf(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                System.out.println("Place: ${place.name}, ${place.latLng}")
                //createMarquer(place.latLng)

            }

            override fun onError(status: Status) {
                System.out.println("Error $status")
            }
        })

           _binding?.bt?.setOnClickListener {
            System.out.println("Sis aadadsdasd")
            System.out.println(map.cameraPosition)
        }
*/



    }
}