package com.alercom.app.ui.user.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.User
import com.alercom.app.repositories.UserRepository
import com.alercom.app.request.CreateUserRequest
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.user.OnUserResponse
import com.alercom.app.response.user.UserResponse
import com.alercom.app.response.user.UserResult

class EditUserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userEditResult = MutableLiveData<UserResult>()
    val userEditResult: LiveData<UserResult> = _userEditResult

    private val _userUpdateResult = MutableLiveData<UserResult>()
    val userUpdateResult: LiveData<UserResult> = _userUpdateResult

    fun getAuthUser()  {
        userRepository.getAuthUser(object : OnUserResponse {
            override fun success(user: User?) {
                _userEditResult.value = UserResult(success  = user)
            }

            override fun unautorize(unautorize: ErrorResponse) {
                _userEditResult.value = UserResult(unautorize = unautorize)
            }

            override fun error(auth: ErrorResponse) {

            }

            override fun errors(errors: ArrayList<String>?) {
                _userEditResult.value = UserResult(errors = errors)
            }

        })
    }

    fun update(user: CreateUserRequest,id:Int)  {
        userRepository.update(user!!,id,object : OnUserResponse {
            override fun success(user: User?) {
                _userUpdateResult.value = UserResult(success = user)
            }


            /*  override fun success(user: Result.Success<Response<UserResponse>>) {
                  _userResult.value = UserResult(success  = user.data.body().lastname)
              }**/

            override fun unautorize(unautorize: ErrorResponse) {
                //_userResult.value = UserResult(unautorize = unautorize)
            }

            override fun error(auth: ErrorResponse) {
                TODO("Not yet implemented")
            }

            override fun errors(errors: ArrayList<String>?) {
                _userUpdateResult.value = UserResult(errors = errors)
            }

        })
    }
}