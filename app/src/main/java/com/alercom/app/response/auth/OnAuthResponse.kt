package com.alercom.app.response.auth


import com.alercom.app.data.Result
import com.alercom.app.response.ErrorResponse
import retrofit2.Response

interface OnAuthResponse {
    fun auth(auth: Result.Success<Response<AuthResponse>>)
    fun unautorize(auth: ErrorResponse)
    fun error(auth: ErrorResponse)
}