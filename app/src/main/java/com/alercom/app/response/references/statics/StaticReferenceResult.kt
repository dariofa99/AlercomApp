package com.alercom.app.response.references.statics

import com.alercom.app.data.model.Reference
import com.alercom.app.response.ErrorResponse


data class StaticReferenceResult(
    val success: List<Reference>? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)
