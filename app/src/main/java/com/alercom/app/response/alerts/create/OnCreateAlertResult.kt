package com.alercom.app.response.alerts.create

import com.alercom.app.data.model.Alert
import com.alercom.app.response.ErrorResponse


interface OnCreateAlertResult {
    fun success(eventReport: Alert?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}