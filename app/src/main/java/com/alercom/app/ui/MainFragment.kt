package com.alercom.app.ui

import android.content.Context
import android.content.Intent
import com.alercom.app.AlercomApp.Companion.prefs
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alercom.app.R
import com.alercom.app.databinding.MainFragmentBinding
import com.alercom.app.ui.login.LoginActivity

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.btnProfile?.setOnClickListener {
            findNavController().navigate(androidx.media.R.id.action0)

        }

        _binding?.btnReporto?.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_AlertFragment)
        }

        _binding?.btnVerAlertas?.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_ListAlertFragment)
        }
        _binding?.btnSalir?.setOnClickListener {
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