package com.alercom.app.response.references.statics

import com.alercom.app.data.model.Reference
import com.alercom.app.response.ErrorResponse


interface OnStaticReferenceResponse {
    fun success(eventType: List<Reference>?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}