package com.alercom.app.ui.alerts.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alercom.app.MainActivity
import com.alercom.app.R
import com.alercom.app.adapter.AlertListAdapter
import com.alercom.app.data.model.Alert
import com.alercom.app.databinding.ListAlertFragmentBinding
import kotlinx.android.synthetic.main.action_bar_toolbar.view.*
import kotlinx.android.synthetic.main.edit_user_fragment.*

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
                val recycler = _binding?.recyclerListAlert
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = AlertListAdapter(eventResult.success) { alert -> onClickListener(alert)
               }
            }
        })


    }

    private fun onClickListener(alert: Alert){
        if(arguments?.getString("data").equals("my")){
            var bundle = Bundle()
            bundle.putInt("alertId",alert.id!!)
            bundle.putInt("categoryId",alert.eventType?.categoryId!!)
            findNavController().navigate(R.id.action_ListAlertFragment_to_EditAlertFragment,bundle)
        }else{
            var bundle = Bundle()
            bundle.putInt("alertId",alert.id!!)
            bundle.putInt("categoryId",alert.eventType?.categoryId!!)
            findNavController().navigate(R.id.action_listAlertFragment_to_showEventFragment,bundle)
        }


     /*   getNavController()?.navigate(
            ListAlertFragmentDirections.actionListAlertFragmentToEditAlertFragment(
                alertId = alert.id!!,
                categoryId = alert.eventType?.categoryId!!
            )
        )*/


    }

    private fun getNavController(): NavController?{
        return  (activity as? MainActivity)?.navController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var toolbarText = "Alertas"
        if(arguments?.getString("data").equals("my")){
            toolbarText = "Mis alertas"
        }

        _binding?.toolbar?.apply {
            textTooblar.text = toolbarText
            toolbar.btn_Back.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        viewModel.index(arguments?.getString("data"))
    }
}