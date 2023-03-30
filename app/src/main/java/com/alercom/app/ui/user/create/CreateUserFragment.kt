package com.alercom.app.ui.user.create

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alercom.app.R
import com.alercom.app.data.model.User
import com.alercom.app.databinding.CreateUserFragmentBinding
import com.alercom.app.request.CreateUserRequest


class CreateUserFragment : Fragment() {

    companion object {
        fun newInstance() = CreateUserFragment()
    }

    private lateinit var viewModelCreate: CreateUserViewModel
    private var _binding: CreateUserFragmentBinding?  = null
    private val binding get() = _binding!!
    private var user : User? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateUserFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelCreate = ViewModelProvider(this, CreateUserViewModelFactory())[CreateUserViewModel::class.java]

        viewModelCreate.userCreateResult.observe(this@CreateUserFragment, Observer {
            val userEditResult = it ?: return@Observer
            if(userEditResult.success!=null){
                user = userEditResult.success
                showDialog("Registro exitoso!",getString(R.string.email_confirmation))
                _binding?.loader?.apply {myLoader.visibility = View.GONE}
                findNavController().navigateUp()
            }
            if(userEditResult.errors!=null){
                var cadena = ""
                userEditResult.errors.forEach {cadena += "$it \n"}
                showDialog("Importante!","$cadena")
                _binding?.loader?.apply {myLoader.visibility = View.GONE}
            }
            if(userEditResult.error!=null){
                showMessage("${userEditResult.error.error}")
                _binding?.loader?.apply {myLoader.visibility = View.GONE}
            }
        })




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.toolbar?.apply {
            textTooblar.text = "REGISTRO"
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        _binding?.btnStoreUser?.setOnClickListener {

            if(!_binding?.aceptedPrivacy?.isChecked!!){
                showDialog("Atención","Debes aceptar la política de privacidad.")
            }else{
                viewModelCreate.store(
                    CreateUserRequest(
                        name = _binding?.name?.text.toString(),
                        lastname = _binding?.lastName?.text.toString(),
                        username = _binding?.username?.text.toString(),
                        email = _binding?.email?.text.toString(),
                        phoneNumber = _binding?.phoneNumber?.text.toString(),
                        password = _binding?.password?.text.toString(),
                        confirmpassword = _binding?.confirmPassword?.text.toString()
                    ))

                _binding?.loader?.apply { myLoader.visibility = View.VISIBLE }
            }


        }
    }

    private fun showMessage(msg:String) {
        Toast.makeText(requireContext(),"$msg", Toast.LENGTH_LONG).show()

    }

    private fun showDialog(title:String,msg:String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("$title")
        builder.setMessage(msg)
            .setPositiveButton("Entendido",DialogInterface.OnClickListener() {
                    dialogInterface: DialogInterface, i: Int ->

            }).show()

    }

}