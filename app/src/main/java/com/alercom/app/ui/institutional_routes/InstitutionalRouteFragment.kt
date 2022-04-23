package com.alercom.app.ui.institutional_routes

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
import com.alercom.app.adapter.InstitutionRouteAdapter
import com.alercom.app.data.model.InstitutionalRoute
import com.alercom.app.databinding.InstitutionalRouteFragmentBinding
import kotlinx.android.synthetic.main.action_bar_toolbar.view.*
import kotlinx.android.synthetic.main.edit_user_fragment.*

class InstitutionalRouteFragment : Fragment() {

    companion object {
        fun newInstance() = InstitutionalRouteFragment()
    }

    private lateinit var viewModel: InstitutionalRouteViewModel
    private var _binding: InstitutionalRouteFragmentBinding?  = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InstitutionalRouteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, InstitutionalRouteViewModelFactory())
            .get(InstitutionalRouteViewModel::class.java)

        viewModel.institutionalRouteResult.observe(this@InstitutionalRouteFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                System.out.println(eventResult.success)
                val recycler = _binding?.recyclerInstitutionalEvent
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = InstitutionRouteAdapter(eventResult.success) { institutionalRoute -> onClickListener(institutionalRoute)}
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.apply {
            textTooblar.text = "Rutas institucionales"
            toolbar.btn_Back.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        viewModel.index()

    }

    private fun onClickListener(institutionalRoute: InstitutionalRoute){

        getNavController()?.navigate(
            InstitutionalRouteFragmentDirections.actionInstitutionalRouteFragmentToInstitutionalRoutePageFragment(
               namePage = institutionalRoute.routeName,
                url = institutionalRoute.routeUrl
                ))
    }

    private fun getNavController(): NavController?{
        return  (activity as? MainActivity)?.navController
    }

}