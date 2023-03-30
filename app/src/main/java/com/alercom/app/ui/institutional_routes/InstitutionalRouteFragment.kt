package com.alercom.app.ui.institutional_routes

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alercom.app.MainActivity
import com.alercom.app.adapter.InstitutionRouteAdapter
import com.alercom.app.data.model.InstitutionalRoute
import com.alercom.app.databinding.InstitutionalRouteFragmentBinding
import com.alercom.app.ui.login.LoginActivity
import com.alercom.app.network.Prefs



class InstitutionalRouteFragment : Fragment() {

    companion object {
        fun newInstance() = InstitutionalRouteFragment()
        lateinit var prefs: Prefs
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
        prefs = Prefs(requireContext())
        viewModel = ViewModelProvider(this, InstitutionalRouteViewModelFactory())[InstitutionalRouteViewModel::class.java]

        viewModel.institutionalRouteResult.observe(this@InstitutionalRouteFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                val recycler = _binding?.recyclerInstitutionalEvent
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = InstitutionRouteAdapter(eventResult.success) { institutionalRoute -> onClickListener(institutionalRoute)}
                _binding?.loader?.apply {myLoader?.visibility = View.GONE}
            }

            if(eventResult.unautorize!=null){
                prefs.saveToken("");
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                Toast.makeText(requireContext(),eventResult.unautorize.error, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.apply {
            textTooblar.text = "Rutas institucionales"
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        _binding?.loader?.apply {myLoader?.visibility = View.VISIBLE}
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