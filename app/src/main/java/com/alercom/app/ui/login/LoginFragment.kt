package com.alercom.app.ui.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.alercom.app.AlercomApp
import com.alercom.app.MainActivity
import com.alercom.app.databinding.FragmentLoginBinding
import com.alercom.app.R
import com.alercom.app.network.Prefs
import com.alercom.app.ui.institutional_routes.InstitutionalRouteFragmentDirections


class LoginFragment : Fragment() {

    companion object{
        lateinit var prefs: Prefs
    }

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    val app = requireContext() as AlercomApp

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
        prefs = Prefs(context = requireContext())
      //  prefs.wipe()
        if(!prefs.getPrivacyPolicy()){
            findNavController().navigate(R.id.action_loginFragment_to_privacyPolicyFragment)
        }
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]



        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.btnLogin
        val loadingProgressBar = binding.Btnloading
/*
        loginViewModel.loginForm.observe(viewLifecycleOwner, Observer {
            showLoginUnautorize("funciona el login ${it.lastname}")
        })

 */

/*
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
            */

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
                //app.room.getUserDao().store()
                updateUiWithUser(loginResult.success)
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
        })



        loginViewModel.loginAnonimusResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer
            _binding?.loader?.apply { myLoader.visibility = View.GONE }
            //loading?.visibility = View.GONE
            //  login?.visibility = View.VISIBLE
            if (loginResult.unautorize != null) {
                loginResult.unautorize.error?.let { it1 -> showLoginUnautorize(it1) }
            }
            if (loginResult.error != null) {
                loginResult.error?.error.let { it1 -> showLoginFailed(it1) }
            }
            if (loginResult.success != null) {

                binding.Btnloading.visibility = View.GONE
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
            binding.btnLogin?.visibility = View.GONE
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
        _binding?.btnRegistrarse?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createUserFragment)
        }
        _binding?.btnReportAnonimo?.setOnClickListener{
            _binding?.loader?.apply { myLoader.visibility = View.VISIBLE }
            loginViewModel.loginAnonimus()
        }

        _binding?.btnPrivacyPolicy?.setOnClickListener {
            getNavController()?.navigate(

                LoginFragmentDirections.actionLoginFragmentToInstitutionalRoutePageFragment(
                    namePage = "Política de privacidad",
                    url = "https://api.alercom.org/policy"
                )
            )


        }

    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = "${getString(R.string.welcome)}"
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
    }


    private fun showLoginFailed(message: String?) {
        binding.Btnloading.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoginUnautorize( string: String) {
        binding.Btnloading.visibility = View.GONE
       Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
    }

    private fun getNavController(): NavController?{
        return  (activity as? LoginActivity)?.navController
    }
}