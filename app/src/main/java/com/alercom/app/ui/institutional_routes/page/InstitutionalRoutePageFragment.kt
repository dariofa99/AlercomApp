package com.alercom.app.ui.institutional_routes.page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alercom.app.databinding.FragmentInstitutionalRoutePageBinding
import kotlinx.android.synthetic.main.action_bar_toolbar.view.*
import kotlinx.android.synthetic.main.edit_user_fragment.*
import kotlinx.android.synthetic.main.fragment_institutional_route_page.*
import kotlinx.android.synthetic.main.fragment_institutional_route_page.toolbar

class InstitutionalRoutePageFragment : Fragment() {

    private val args: InstitutionalRoutePageFragmentArgs by navArgs()
    private var _binding: FragmentInstitutionalRoutePageBinding?  = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstitutionalRoutePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = InstitutionalRoutePageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.apply {
            textTooblar.text = args.namePage
            toolbar.btn_Back.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        _binding?.myWebView?.webChromeClient = object : WebChromeClient(){

        }
        _binding?.myWebView?.webViewClient = object : WebViewClient(){

        }

        
        val setting:WebSettings = _binding?.myWebView!!?.settings
        setting.javaScriptEnabled = true

        args?.url?.let { myWebView.loadUrl(it) }


        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(_binding!!.myWebView.canGoBack()){
                    _binding!!.myWebView.goBack()
                }else{
                    findNavController().navigateUp()
                }
            }

        })

    }
}
