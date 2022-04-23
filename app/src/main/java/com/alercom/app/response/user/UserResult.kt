package com.alercom.app.response.user

import com.alercom.app.data.model.User
import com.alercom.app.response.ErrorResponse



data class UserResult(
    val success: User? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)