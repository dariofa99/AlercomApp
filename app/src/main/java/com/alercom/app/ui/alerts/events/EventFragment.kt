package com.alercom.app.ui.alerts.events

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alercom.app.MainActivity
import com.alercom.app.R
import com.alercom.app.adapter.EventTypeAdapter
import com.alercom.app.data.model.EventType
import com.alercom.app.databinding.EventFragmentBinding
import com.alercom.app.ui.alerts.location.LocationFragmentArgs

class EventFragment : Fragment() {

    companion object {
        fun newInstance() = EventFragment()
    }

    private lateinit var viewModel: EventViewModel

    private var _binding: EventFragmentBinding?  = null
    private val binding get() = _binding!!
    private lateinit var Mcontext: Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Mcontext = context

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = this.requireActivity().actionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = EventFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, EventViewModelFactory())
            .get(EventViewModel::class.java)

        viewModel.eventResult.observe(this@EventFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                val recycler = _binding?.recyclerEventType
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = EventTypeAdapter(eventResult.success) { eventType -> onClickListener(eventType)}
            }

        })

    }

    private fun onClickListener(eventType: EventType){
              getNavController()?.navigate(

             EventFragmentDirections.actionMainFragmentToAlertFragment(
                   eventTypeId = eventType.id!!,
                    eventName = eventType.eventTypeName,
                    eventDescription = eventType.eventTypeDescription,

             ))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.index()

    }

    private fun getNavController(): NavController?{
        return  (activity as? MainActivity)?.navController
    }

}