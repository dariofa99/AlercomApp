package com.alercom.app.response.events

import com.alercom.app.data.model.EventType
import com.alercom.app.response.ErrorResponse


data class EventTypeResult(
    val success: List<EventType>? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)
