package com.alercom.app.data.services

import com.alercom.app.data.model.User
import com.alercom.app.data.model.auth.Auth
import com.alercom.app.response.auth.AuthResponse
import com.alercom.app.response.user.UserResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
/*
class LoginService @Inject constructor(private val api:LoginApiClient) {
    suspend fun login(username:String, password:String): AuthResponse {


        return withContext(Dispatchers.IO) {
            val jo = JsonObject()
            jo.addProperty("username", username)
            jo.addProperty("password", password)
            System.out.println(jo)
            val response = api.login(jo)
            response.body()!!
        }
    }
}

 */