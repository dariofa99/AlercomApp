package com.alercom.app.response.institutions

import com.alercom.app.data.model.Institution
import com.alercom.app.response.ErrorResponse

data class InstitutionsResult (
    val success: List<Institution>? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)