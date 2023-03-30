package com.alercom.app.response.alerts.list

import com.alercom.app.resources.paginator.Paginator
import com.alercom.app.response.ErrorResponse



data class ListAlertResult(
    val success: Paginator? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)
