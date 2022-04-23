package com.alercom.app.ui

import com.alercom.app.network.Prefs
import android.content.Intent
import com.alercom.app.AlercomApp.Companion.prefs
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alercom.app.AlercomApp
import com.alercom.app.R
import com.alercom.app.databinding.MainFragmentBinding
import com.alercom.app.ui.alerts.list.ListAlertFragment
import com.alercom.app.ui.login.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.action_bar_toolbar.view.*
import kotlinx.android.synthetic.main.edit_user_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()

    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: Prefs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(prefs?.getUserName().toString() == "anonimus"){
           // findNavController().navigate(R.id.action_MainFragment_to_AlertFragment)
            _binding?.btnProfile?.visibility = View.GONE
            _binding?.btnViewMyAlerts?.visibility = View.GONE
            _binding?.btnDataProtection?.visibility = View.GONE
            _binding?.btnViewAlerts?.visibility = View.GONE
            _binding?.btnInstRoutes?.visibility = View.GONE
        }

        _binding?.toolbar?.apply {
            textTooblar.text = "ALERCOM"
            //toolbar.btn_Back.setImageResource(R.drawable.conect)
            Picasso.with(context)
                .load(R.drawable.conect).resize(150,150)
                .into(_binding?.toolbar?.btnBack);


        }

        _binding?.btnProfile?.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_editUserFragment)

        }
        _binding?.btnReport?.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_AlertFragment)
        }
        _binding?.btnViewMyAlerts?.setOnClickListener {
            var bundle = Bundle()
            bundle.putString("data","my")
            findNavController().navigate(R.id.action_MainFragment_to_ListMyAlertFragment,bundle)
        }
        _binding?.btnDataProtection?.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_dataProtectionFragment)
        }
        _binding?.btnInstRoutes?.setOnClickListener{
            findNavController().navigate(R.id.action_MainFragment_to_institutionalRouteFragment)
        }
        _binding?.btnViewAlerts?.setOnClickListener{
            var bundle = Bundle()
            bundle.putString("data","all")
            findNavController().navigate(R.id.action_MainFragment_to_listAlertFragment,bundle)
        }

        _binding?.btnLogout?.setOnClickListener {
            prefs.wipe();
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}