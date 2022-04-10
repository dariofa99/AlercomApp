package com.alercom.app.services

import com.alercom.app.response.auth.AuthResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/v1/login")
    fun login(@Body dataInicioSesion: JsonObject): Call<AuthResponse>
}