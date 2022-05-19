package com.alercom.app.ui.data_protection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alercom.app.network.Prefs
import com.alercom.app.adapter.ViewPageAdapter
import com.alercom.app.databinding.FragmentDataProtectionBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.action_bar_toolbar.view.*
import kotlinx.android.synthetic.main.edit_user_fragment.*


class DataProtectionFragment : Fragment() {
    companion object {
        lateinit var prefs: Prefs
        fun newInstance() =
            DataProtectionFragment()
    }

    private val adapter by lazy { ViewPageAdapter(requireActivity()) }
    private var _binding: FragmentDataProtectionBinding?  = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataProtectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.apply {
            textTooblar.text = "Protección de datos"
            toolbar.btn_Back.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        _binding?.myPager?.adapter = adapter
        val tabLayoutMediator = _binding?.myTabLayout?.let {
            _binding?.myPager?.let { it1 ->
                TabLayoutMediator(it, it1,
                    TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                        when(position + 1){
                            1 -> {
                                tab.text = "Protección de datos"
                            }
                            2 -> {
                                tab.text = "Tratamiento de datos"
                            }
                        }

                    }
                )
            }
        }
        tabLayoutMediator?.attach()
    }
}