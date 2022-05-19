package com.alercom.app.domain.auth

import com.alercom.app.data.repositories.LoginRepository
import javax.inject.Inject
/*
class AuthLoginCase @Inject constructor(
    private val repository: LoginRepository,

) {

    suspend operator fun invoke(username:String, password:String): User {
        val auth = repository.login(username,password)
        val user = User(
            name = auth.authUser?.name
        )
        return user

    }
}

 */