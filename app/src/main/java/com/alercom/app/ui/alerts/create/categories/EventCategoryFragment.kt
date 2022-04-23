package com.alercom.app.ui.alerts.create.categories

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
import com.alercom.app.adapter.EventCategoryAdapter
import com.alercom.app.data.model.Reference
import com.alercom.app.databinding.EventCategoryFragmentBinding

import kotlinx.android.synthetic.main.action_bar_toolbar.view.*
import kotlinx.android.synthetic.main.edit_user_fragment.*
import kotlinx.android.synthetic.main.loading.*

class EventCategoryFragment : Fragment() {

    companion object {
        fun newInstance() = EventCategoryFragment()
    }

    private lateinit var viewModelEvent: EventCategoryViewModel
    private var _binding: EventCategoryFragmentBinding?  = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = this.requireActivity().actionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = EventCategoryFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelEvent = ViewModelProvider(this, EventCategoryViewModelFactory())
            .get(EventCategoryViewModel::class.java)

     /*   viewModel.eventResult.observe(this@EventFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                val recycler = _binding?.recyclerEventType
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = EventTypeAdapter(eventResult.success) { eventType -> onClickListener(eventType)}
            }

        })*/

        viewModelEvent.eventCategories.observe(this@EventCategoryFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
               _binding?.loader.apply {myLoader.visibility = View.GONE}
                val recycler = _binding?.recyclerEventType
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = EventCategoryAdapter(eventResult.success) { eventType -> onClickListener(eventType)}
            }

        })

    }

    private fun onClickListener(eventType: Reference){
              getNavController()?.navigate(

             EventCategoryFragmentDirections.actionMainFragmentToAlertFragment(
                   eventTypeId = eventType.id!!,
                    eventName = eventType.referenceName,
                    eventDescription = eventType.referenceDescription,

             ))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.loader.apply {myLoader.visibility = View.VISIBLE}

        _binding?.toolbar?.apply {
            textTooblar.text = "YO REPORTO"
            toolbar.btn_Back.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        viewModelEvent.getEventsCategories()

    }

    private fun getNavController(): NavController?{
        return  (activity as? MainActivity)?.navController
    }

}