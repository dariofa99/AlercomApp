package com.alercom.app.response.auth

import com.alercom.app.response.ErrorResponse
import com.alercom.app.ui.login.LoggedInUserView


/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: LoggedInUserView? = null,
        val error: ErrorResponse? = null,
        val unautorize: ErrorResponse? = null
)