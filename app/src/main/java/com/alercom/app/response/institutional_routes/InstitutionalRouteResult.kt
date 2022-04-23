package com.alercom.app.response.institutional_routes

import com.alercom.app.data.model.InstitutionalRoute
import com.alercom.app.data.model.Reference
import com.alercom.app.response.ErrorResponse

 data class InstitutionalRouteResult (
    val success: List<InstitutionalRoute>? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
 )