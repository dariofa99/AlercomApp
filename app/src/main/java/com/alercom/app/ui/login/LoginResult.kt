package com.alercom.app.ui.login

import com.alercom.app.response.ErrorResponse

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null
)