package com.alercom.app.response.alerts.edit

import com.alercom.app.data.model.Alert
import com.alercom.app.data.model.Reference
import com.alercom.app.response.ErrorResponse


interface OnEditAlertResult {
    fun success(alert: Alert?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}