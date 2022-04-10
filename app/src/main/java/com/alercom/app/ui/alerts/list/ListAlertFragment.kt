package com.alercom.app.ui.alerts.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alercom.app.MainActivity
import com.alercom.app.R
import com.alercom.app.adapter.AlertListAdapter
import com.alercom.app.adapter.EventTypeAdapter
import com.alercom.app.data.model.Alert
import com.alercom.app.data.model.EventType
import com.alercom.app.databinding.CreateAlertFragmentBinding
import com.alercom.app.databinding.ListAlertFragmentBinding
import com.alercom.app.ui.alerts.CreateAlertViewModel
import com.alercom.app.ui.alerts.CreateAlertViewModelFactory
import com.alercom.app.ui.alerts.events.EventFragmentDirections
import com.app.alercom.adapter.AffectsRangeSpinnerAdapter

class ListAlertFragment : Fragment() {

    companion object {
        fun newInstance() = ListAlertFragment()
    }

    private lateinit var viewModel: ListAlertViewModel
    private var _binding: ListAlertFragmentBinding?  = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListAlertFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ListAlertViewModelFactory())
            .get(ListAlertViewModel::class.java)


        viewModel.listAlertResult.observe(this@ListAlertFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                System.out.println("Hol soledad")
                val recycler = _binding?.recyclerListAlert
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = AlertListAdapter(eventResult.success) { alert -> onClickListener(alert)
               }
            }
        })
    }

    private fun onClickListener(alert: Alert){
        getNavController()?.navigate(
            ListAlertFragmentDirections.actionListAlertFragmentToEditAlertFragment(
                alertId = alert.id!!
            )
        )


    }

    private fun getNavController(): NavController?{
        return  (activity as? MainActivity)?.navController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.index()
    }
}