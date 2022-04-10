package com.alercom.app.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.alercom.app.MainActivity
import com.alercom.app.databinding.ActivityLoginBinding

import com.alercom.app.R

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private var _binding: ActivityLoginBinding? = null
   // private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.btnLogin
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login?.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                //username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
              //password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer


            loading?.visibility = View.GONE
            //  login?.visibility = View.VISIBLE
            if (loginResult.unautorize != null) {
                loginResult.unautorize.error?.let { it1 -> showLoginUnautorize(it1) }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            if (loginResult.error != null) {
                loginResult.error?.error.let { it1 -> showLoginFailed(it1) }
                //showLoginFailed(loginResult.error)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            if (loginResult.success != null) {
                binding.loading.visibility = View.GONE
                binding.btnLogin?.visibility = View.VISIBLE
                updateUiWithUser(loginResult.success)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

            }
            //setResult(Activity.RESULT_OK)
            //finish()
        })

     /*   username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }
*/
/*
        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }
}*/
            login?.setOnClickListener {
                loading.visibility = View.VISIBLE
                System.out.println("Aqui $username")
                loginViewModel.login(username.text.toString(), password.text.toString())
            }

    }

    private fun showLoginUnautorize( string: String) {
        binding.loading.visibility = View.GONE
        // binding.btnLogin.visibility = View.VISIBLE
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun showLoginFailed(message: String?) {
        binding.loading.visibility = View.GONE
        // binding.btnLogin.visibility = View.VISIBLE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}