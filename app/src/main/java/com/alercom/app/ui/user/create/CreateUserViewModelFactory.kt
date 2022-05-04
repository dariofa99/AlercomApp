package com.alercom.app.ui.user.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.data.repositories.UserRepository

class CreateUserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateUserViewModel::class.java)) {
            return CreateUserViewModel(
                userRepository = UserRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}