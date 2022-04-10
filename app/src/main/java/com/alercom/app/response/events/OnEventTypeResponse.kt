package com.app.alercom.response.events

import com.alercom.app.data.model.EventType
import com.alercom.app.response.ErrorResponse


interface OnEventTypeResponse {
    fun success(eventType: List<EventType>?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}