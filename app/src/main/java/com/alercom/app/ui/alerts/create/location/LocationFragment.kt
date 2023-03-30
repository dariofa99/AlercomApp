package com.alercom.app.ui.alerts.create.location

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alercom.app.MainActivity
import com.alercom.app.data.model.Reference
import com.alercom.app.data.model.Town
import com.alercom.app.databinding.LocationFragmentBinding
import com.app.alercom.adapter.DeparmentsSpinnerAdapter
import com.app.alercom.adapter.TownsSpinnerAdapter


class LocationFragment : Fragment(), AdapterView.OnItemClickListener {

    companion object {
        fun newInstance() = LocationFragment()
    }

    private val args: LocationFragmentArgs by navArgs()
    private lateinit var viewModel: LocationViewModel

    private var _binding: LocationFragmentBinding?  = null
    private val binding get() = _binding!!
    private lateinit var Mcontext: Context
    private var deptAdapter :  DeparmentsSpinnerAdapter? = null
    private var townAdapter :  TownsSpinnerAdapter? = null
    private var _deparment : Reference? = null
    private var _town : Town? = null


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
        _binding = LocationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, LocationViewModelFactory())
            .get(LocationViewModel::class.java)

        viewModel.staticReferenceResult.observe(this@LocationFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {

                deptAdapter = DeparmentsSpinnerAdapter(Mcontext,eventResult.success) //{ eventType -> onClickListener(eventType)}
                _binding?.departmentSpinner?.setAdapter(deptAdapter)
            }
        })

        viewModel.townsResult.observe(this@LocationFragment, Observer {
            val townResult  = it ?: return@Observer
            if(townResult.success!=null){
                _binding?.townsSpinner?.setText("")
                townAdapter = TownsSpinnerAdapter(Mcontext,townResult.success) //{ eventType -> onClickListener(eventType)}
                _binding?.townsSpinner?.setAdapter(townAdapter)

            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, LocationViewModelFactory())[LocationViewModel::class.java]
        _binding?.toolbar?.apply {
            textTooblar.text = "Creando alerta"
            btnBack?.setOnClickListener {
                findNavController().navigateUp()
            }
        }
       _binding?.eventName?.text = args.eventName
        viewModel.getDepartments()

       _binding?.departmentSpinner?.onItemClickListener = this@LocationFragment
        _binding?.townsSpinner?.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            _town = townAdapter?.getItem(position)
        }
        _binding?.btnNextCreateAlert?.setOnClickListener {
            val departmentId = _deparment?.id
            val townId = _town?.id

       if(validateForm()){
               getNavController()?.navigate(LocationFragmentDirections.actionLocationFragmentToCreateAlert(
                   eventTypeId = args.eventTypeId,
                   departmentId = departmentId!!,
                   townId = townId!!,
                   eventName = args.eventName,
                   eventDescription = args.eventDescription
               ))
            }



        }


    }

    private fun getNavController(): NavController?{
        return  (activity as? MainActivity)?.navController
    }


    private  fun validateForm() : Boolean{
        if(_binding?.departmentSpinner?.text!!.isEmpty()){
            _binding?.lDepartmentSpinner?.error = "El departamento no puede estar vacio"
            return false
        }else{
            _binding?.lDepartmentSpinner?.error = null
        }

        if(_binding?.townsSpinner?.text!!.isEmpty()){
            _binding?.lTownsSpinner?.error = "El municipio no puede estar vacio"
            return false
        }else{
            _binding?.lTownsSpinner?.error = null
        }


        return  true
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val id = deptAdapter?.getItem(position)?.id
        _deparment = deptAdapter?.getItem(position)
        viewModel.getTownByDepId(id = id!!)
    }


}