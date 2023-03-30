package com.alercom.app.response.alerts.list


import com.alercom.app.resources.paginator.Paginator
import com.alercom.app.response.ErrorResponse


interface OnListAlertResult {
    fun success(paginator: Paginator?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}