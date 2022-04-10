package com.alercom.app.response.alerts.list

import com.alercom.app.data.model.Alert
import com.alercom.app.response.ErrorResponse


interface OnListAlertResult {
    fun success(alert: List<Alert>?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}