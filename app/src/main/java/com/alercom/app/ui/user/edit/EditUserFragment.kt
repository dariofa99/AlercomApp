package com.alercom.app.ui.user.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.alercom.app.data.model.User
import com.alercom.app.databinding.EditUserFragmentBinding
import com.alercom.app.request.CreateUserRequest
import kotlinx.android.synthetic.main.action_bar_toolbar.view.*
import kotlinx.android.synthetic.main.edit_user_fragment.*
import kotlinx.android.synthetic.main.loading.*



class EditUserFragment : Fragment() {

    companion object {
        fun newInstance() = EditUserFragment()
    }

    private lateinit var viewModel: EditUserViewModel
    private var _binding: EditUserFragmentBinding?  = null
    private val binding get() = _binding!!
    private var user : User? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditUserFragmentBinding.inflate(inflater, container, false)

      //  MyTooblar().show(activity as AppCompatActivity,"User Edit",true)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* val tooblar:Toolbar? = view?.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as MainActivity).setSupportActionBar(tooblar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)*/

        // MyTooblar().show(activity as AppCompatActivity,"User Edit",true)
          viewModel = ViewModelProvider(this, EditUserViewModelFactory())[EditUserViewModel::class.java]

        viewModel.userEditResult.observe(this@EditUserFragment, Observer {
            val userEditResult = it ?: return@Observer
            if(userEditResult.success!=null){
                user = userEditResult.success
                _binding?.name?.setText(user?.name)
                _binding?.lastName?.setText(user?.lastname)
                _binding?.email?.setText(user?.email)
                _binding?.phoneNumber?.setText(user?.phoneNumber)
                _binding?.username?.setText(user?.username)
                _binding?.loader.apply {
                    myLoader.visibility = View.GONE
                }
            }
        })

        viewModel.userUpdateResult.observe(this@EditUserFragment, Observer {
            val userUpdateResult = it ?: return@Observer

            _binding?.loader.apply {
                myLoader.visibility = View.GONE
            }
            if(userUpdateResult.success!=null){
                user = userUpdateResult.success
                _binding?.checkboxChangePass?.isChecked = false
                setContentPassword()
                showMessage("Datos actualizados con éxito")
                _binding?.loader.apply {
                    myLoader.visibility = View.GONE
                }
            }

            if (userUpdateResult.errors != null) {

                userUpdateResult.errors.forEach {
                    showMessage("$it")
                }

            }
        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.toolbar?.apply {
            textTooblar.text = "ACTUALIZAR PERFIL"
            toolbar.btn_Back.setOnClickListener {
                findNavController().navigateUp()
            }
        }

        _binding?.contentInputsPassword?.visibility = View.GONE
        _binding?.loader.apply {myLoader.visibility = View.VISIBLE}
        viewModel.getAuthUser()

        _binding?.checkboxChangePass?.setOnClickListener {
            setContentPassword()
        }

        _binding?.btnSaveUser?.setOnClickListener {
            if (!_binding?.checkboxChangePass!!.isChecked) {
                viewModel.update(
                    CreateUserRequest(
                        name = _binding?.name?.text.toString(),
                        lastname = _binding?.lastName?.text.toString(),
                        username = _binding?.username?.text.toString(),
                        email = _binding?.email?.text.toString(),
                        phoneNumber = _binding?.phoneNumber?.text.toString()
                    ), user?.id!!
                )
            } else {
                viewModel.update(
                    CreateUserRequest(
                        name = _binding?.name?.text.toString(),
                        lastname = _binding?.lastName?.text.toString(),
                        username = _binding?.username?.text.toString(),
                        email = _binding?.email?.text.toString(),
                        phoneNumber = _binding?.phoneNumber?.text.toString(),
                        password = _binding?.password?.text.toString(),
                        oldPassword = _binding?.oldpassword?.text.toString(),
                        confirmpassword = _binding?.confirmPassword?.text.toString()
                    ), user?.id!!
                )
            }
            _binding?.loader.apply { myLoader.visibility = View.VISIBLE }
        }
    }

    private fun showMessage(msg:String) {
        Toast.makeText(requireContext(),"$msg", Toast.LENGTH_LONG).show()

    }

    private fun setContentPassword(){
        if(!_binding?.checkboxChangePass!!.isChecked){
            _binding?.contentInputsPassword?.visibility = View.GONE
            _binding?.password?.text = null
            _binding?.oldpassword?.text = null
            _binding?.confirmPassword?.text = null
        }else{
            _binding?.contentInputsPassword?.visibility = View.VISIBLE

        }
    }

}