package com.alercom.app.response.institutions

import com.alercom.app.data.model.Institution
import com.alercom.app.response.ErrorResponse

interface OnInstitutionsResponse {
    fun success(institutionalRoute: List<Institution>?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}