package com.alercom.app.ui.data_protection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alercom.app.R
import com.alercom.app.databinding.FragmentDataProtectionContentBinding

class DataProtectionContentFragment : Fragment() {

    private var _binding: FragmentDataProtectionContentBinding?  = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataProtectionContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = DataProtectionContentFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.takeIf { it.containsKey("object") }?.apply {
            when(getInt("object")){
                1 -> _binding?.textView?.text = getString(R.string.data_protection)
                2 -> _binding?.textView?.text = getString(R.string.data_tratamiento)

            }

        }
    }
}
