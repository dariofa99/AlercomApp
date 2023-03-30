package com.alercom.app.ui.alerts.list

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alercom.app.MainActivity
import com.alercom.app.R
import com.alercom.app.adapter.AlertListAdapter
import com.alercom.app.data.model.Alert
import com.alercom.app.databinding.ListAlertFragmentBinding
import com.alercom.app.network.Prefs
import com.alercom.app.ui.login.LoginActivity
import kotlin.math.ceil


class ListAlertFragment : Fragment() {

    companion object {
        fun newInstance() = ListAlertFragment()
        lateinit var prefs: Prefs
    }

    private lateinit var viewModel: ListAlertViewModel
    private var _binding: ListAlertFragmentBinding?  = null
    private val binding get() = _binding!!
    private var page : Int = 1
    private var limit : Int? = 2
    private var dataArrayList : ArrayList<Alert> = ArrayList()
    private  var flag : Boolean = true
    private  var pos : Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListAlertFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(requireContext())
        viewModel = ViewModelProvider(this, ListAlertViewModelFactory())[ListAlertViewModel::class.java]


        viewModel.listAlertResult.observe(this@ListAlertFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                var res = (eventResult.success.total?.div(eventResult.success.perPage!!))
                val mod = (eventResult.success.total?.mod(eventResult.success.perPage!!))
                if(mod!! > 0){
                    res = res!! + 1
                }
                limit = res
                if(flag && pos == 1){
                    eventResult.success.data.forEach{
                        dataArrayList.add(it)
                    }
                }

                val recycler = _binding?.recyclerListAlert
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = AlertListAdapter(dataArrayList) { alert -> onClickListener(alert)
               }
                _binding?.loader?.apply {
                    myLoader.visibility = View.GONE
                }
                _binding?.recyclerProgress?.visibility = View.GONE
                //flag = false
            }

            if(eventResult.unautorize!=null){
                prefs.saveToken("");
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                _binding?.loader?.apply {
                    myLoader.visibility = View.GONE
                }
                Toast.makeText(requireContext(),eventResult.unautorize.error, Toast.LENGTH_LONG).show()
            }

        })


    }

    private fun onClickListener(alert: Alert){
        flag = false
        pos = 2
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
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        _binding?.loader?.apply {
            myLoader.visibility = View.VISIBLE
        }

        _binding?.scrollView?.setOnScrollChangeListener(
            View.OnScrollChangeListener { v, scrollX, scrollY, _, _ ->
                flag = true
                pos = 1
                 if (!v.canScrollVertically(1) && page < limit!!){
                     _binding?.recyclerProgress?.visibility = View.VISIBLE
                    page++
                    viewModel.index(arguments?.getString("data"),page)
                 }

            })

        viewModel.index(arguments?.getString("data"),page)
    }
}