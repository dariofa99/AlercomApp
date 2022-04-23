package com.alercom.app.response.user

import com.alercom.app.data.model.User
import com.alercom.app.response.ErrorResponse


interface OnUserResponse {
    fun success(user: User?)
    fun unautorize(auth: ErrorResponse)
    fun error(auth: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}