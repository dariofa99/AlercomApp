package com.alercom.app.response.references.dynamic

import com.alercom.app.data.model.Reference
import com.alercom.app.response.ErrorResponse


interface OnDynamicReferenceResponse {
    fun success(affectsRange: List<Reference>?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}