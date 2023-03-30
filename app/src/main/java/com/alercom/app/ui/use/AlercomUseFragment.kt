package com.alercom.app.ui.use


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alercom.app.adapter.EventCategoryAdapter
import com.alercom.app.adapter.EventCategoryShowAdapter
import com.alercom.app.adapter.InstitutionContactAdapter
import com.alercom.app.data.model.Reference
import com.alercom.app.databinding.FragmentUseAlercomBinding
import com.alercom.app.network.Prefs
import com.alercom.app.ui.login.LoginActivity

class AlercomUseFragment : Fragment() {
    private var _binding: FragmentUseAlercomBinding?  = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = AlercomUseFragment()
        lateinit var prefs: Prefs
    }

    private lateinit var viewModel: AlercomUseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUseAlercomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInstitutions()
        viewModel.getEventsCategories()


        _binding?.toolbar?.apply {
            textTooblar.text = "USO DE ALERCOM"
            _binding?.toolbar?.btnBack?.setOnClickListener {
                findNavController().navigateUp()
            }
        }


        _binding?.loader?.apply {
            myLoader?.visibility = View.VISIBLE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(requireContext())
        viewModel = ViewModelProvider(this,AlercomUseViewModelFactory())[AlercomUseViewModel::class.java]

        viewModel.institutionsResult.observe(this@AlercomUseFragment, Observer {
            val institutionsResult = it ?: return@Observer

            if (institutionsResult.success != null) {
                val recycler = _binding?.recyclerInstitutionalContact
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = InstitutionContactAdapter(institutionsResult.success)
            }

        })


        viewModel.eventCategories.observe(this@AlercomUseFragment, Observer {
            val eventResult = it ?: return@Observer

            if (eventResult.success != null) {
                _binding?.loader?.apply {myLoader?.visibility = View.GONE }
                val recycler = _binding?.recyclerEventType
                recycler?.layoutManager = LinearLayoutManager(requireContext())
                recycler?.adapter = EventCategoryShowAdapter(eventResult.success) { eventType -> onClickListener(eventType)}
            }
            if(eventResult.unautorize!=null){
                prefs.saveToken("");
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                Toast.makeText(requireContext(),eventResult.unautorize.error,Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun onClickListener(institution: Reference) {

    }
}