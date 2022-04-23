package com.alercom.app.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.alercom.app.MainActivity
import com.alercom.app.databinding.FragmentLoginBinding

import com.alercom.app.R

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.btnLogin
        val loadingProgressBar = binding.loading

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer
            //loading?.visibility = View.GONE
            //  login?.visibility = View.VISIBLE
            if (loginResult.unautorize != null) {
                loginResult.unautorize.error?.let { it1 -> showLoginUnautorize(it1) }
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
            if (loginResult.error != null) {
                loginResult.error?.error.let { it1 -> showLoginFailed(it1) }
                //showLoginFailed(loginResult.error)
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
            if (loginResult.success != null) {
                binding.loading.visibility = View.GONE
                binding.btnLogin?.visibility = View.VISIBLE
                updateUiWithUser(loginResult.success)
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
        })

        loginViewModel.loginAnonimusResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer
            //loading?.visibility = View.GONE
            //  login?.visibility = View.VISIBLE
            if (loginResult.unautorize != null) {
                loginResult.unautorize.error?.let { it1 -> showLoginUnautorize(it1) }
                //val intent = Intent(requireContext(), LoginActivity::class.java)
                //startActivity(intent)
            }
            if (loginResult.error != null) {
                loginResult.error?.error.let { it1 -> showLoginFailed(it1) }
                //showLoginFailed(loginResult.error)
               // val intent = Intent(requireContext(), LoginActivity::class.java)
               // startActivity(intent)
            }
            if (loginResult.success != null) {
                binding.loading.visibility = View.GONE
                binding.btnLogin?.visibility = View.VISIBLE
                updateUiWithUser(loginResult.success)
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
        })

     /*   val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }*/
     /*   usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }*/

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
        _binding?.btnRegistrarse?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createUserFragment)
        }
        _binding?.btnReportAnonimo?.setOnClickListener{
            loginViewModel.loginAnonimus()
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }


    private fun showLoginFailed(message: String?) {
        binding.loading.visibility = View.GONE
        // binding.btnLogin.visibility = View.VISIBLE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoginUnautorize( string: String) {
        binding.loading.visibility = View.GONE
        // binding.btnLogin.visibility = View.VISIBLE
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
    }
}