package com.alercom.app.data.services

import com.alercom.app.request.CreateUserRequest
import com.alercom.app.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.*


interface UserService {
    @PUT("api/v1/users/{id}")
    fun update(@Path("id") id: Int?,@Body user: CreateUserRequest): Call<UserResponse>

    @GET("api/v1/user/me")
    fun getUser(): Call<UserResponse>

    @POST("api/v1/register")
    fun store(@Body user: CreateUserRequest): Call<UserResponse>
}