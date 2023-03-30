package com.alercom.app.ui.privacy_policy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.alercom.app.R
import com.alercom.app.databinding.FragmentPrivacyPolicyBinding
import com.alercom.app.databinding.MainFragmentBinding
import com.alercom.app.network.Prefs


/**
 * A simple [Fragment] subclass.
 * Use the [PrivacyPolicyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PrivacyPolicyFragment : Fragment() {
    private var _binding: FragmentPrivacyPolicyBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
        return binding.root

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PrivacyPolicyFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.btnAcceptPrivacyPolicy?.setOnClickListener {
            //Toast.makeText(requireContext(),"Hola boton",Toast.LENGTH_LONG).show()
            prefs.setPrivacyPolicy(true)
            findNavController().navigate(R.id.action_privacyPolicyFragment_to_loginFragment)
        }
    }
}