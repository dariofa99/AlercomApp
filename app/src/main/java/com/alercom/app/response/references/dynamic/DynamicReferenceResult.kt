package com.alercom.app.response.references.dynamic

import com.alercom.app.data.model.Reference
import com.alercom.app.response.ErrorResponse


data class DynamicReferenceResult(
    val success: List<Reference>? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)
