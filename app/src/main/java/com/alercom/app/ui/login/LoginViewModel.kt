package com.alercom.app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns


import com.alercom.app.R
import com.alercom.app.data.Result
import com.alercom.app.repositories.LoginRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.auth.AuthResponse
import com.alercom.app.response.auth.OnAuthResponse
import retrofit2.Response

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _loginAnonimusResult = MutableLiveData<LoginResult>()
    val loginAnonimusResult: LiveData<LoginResult> = _loginAnonimusResult

    fun login(username: String, password: String)  {
        loginRepository.login(username, password,object : OnAuthResponse {
            override fun auth(auth: Result.Success<Response<AuthResponse>>) {
                _loginResult.value = LoginResult(success = LoggedInUserView(auth.data.message()))
            }

            override fun unautorize(unautorize: ErrorResponse) {
                _loginResult.value = LoginResult(unautorize = unautorize)
            }

            override fun error(unautorize: ErrorResponse) {

                _loginResult.value = LoginResult(error = unautorize)
            }
        })


        /* val result = dataSource.login(username, password, object : OnAuthResponse {
             override fun auth(auth: AuthResponse) {
                 System.out.println("Por fin "+auth.accessToken)

             }
         })*/
        /*
        val result =  loginRepository.login(username, password)
        System.out.println("Envio")
        if (result is Result.Success) {
            System.out.println("Aquiiii result")
            System.out.println(result)
            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }*/
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun loginAnonimus() {
        loginRepository.loginAnonimus(object : OnAuthResponse {
            override fun auth(auth: Result.Success<Response<AuthResponse>>) {
                _loginAnonimusResult.value = LoginResult(success = LoggedInUserView(auth.data.message()))
            }

            override fun unautorize(unautorize: ErrorResponse) {
                _loginAnonimusResult.value = LoginResult(unautorize = unautorize)
            }

            override fun error(unautorize: ErrorResponse) {

                _loginAnonimusResult.value = LoginResult(error = unautorize)
            }
        })
    }
    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}