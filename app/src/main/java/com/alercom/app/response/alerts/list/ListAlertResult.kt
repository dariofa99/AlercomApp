package com.alercom.app.response.alerts.list

import com.alercom.app.data.model.Alert
import com.alercom.app.response.ErrorResponse



data class ListAlertResult(
    val success: List<Alert>? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)
