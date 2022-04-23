package com.alercom.app.ui.user.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.User
import com.alercom.app.repositories.UserRepository
import com.alercom.app.request.CreateUserRequest
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.user.OnUserResponse
import com.alercom.app.response.user.UserResult

class CreateUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userCreateResult = MutableLiveData<UserResult>()
    val userCreateResult: LiveData<UserResult> = _userCreateResult

    fun store(user: CreateUserRequest)  {
        userRepository.store(user!!,object : OnUserResponse {
            override fun success(user: User?) {
                _userCreateResult.value = UserResult(success = user)
            }


            override fun unautorize(unautorize: ErrorResponse) {
                //_userResult.value = UserResult(unautorize = unautorize)
            }

            override fun error(error: ErrorResponse) {
                _userCreateResult.value = UserResult(error = error)
            }

            override fun errors(errors: ArrayList<String>?) {
                _userCreateResult.value = UserResult(errors = errors)
            }

        })
    }
}