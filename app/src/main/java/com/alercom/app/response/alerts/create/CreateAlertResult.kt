package com.alercom.app.response.alerts.create

import com.alercom.app.data.model.Alert
import com.alercom.app.response.ErrorResponse



data class CreateAlertResult(
    val success: Alert? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)
