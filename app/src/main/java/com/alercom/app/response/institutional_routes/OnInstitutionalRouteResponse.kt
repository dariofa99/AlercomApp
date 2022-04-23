package com.alercom.app.response.institutional_routes


import com.alercom.app.data.model.InstitutionalRoute
import com.alercom.app.response.ErrorResponse

interface OnInstitutionalRouteResponse {
    fun success(institutionalRoute: List<InstitutionalRoute>?)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}