package com.alercom.app.response.references.towns

import com.alercom.app.data.model.Town
import com.alercom.app.response.ErrorResponse


data class TownResult(
    val success: List<Town>? = null,
    val error: ErrorResponse? = null,
    val unautorize: ErrorResponse? = null,
    val errors: ArrayList<String>? = null
)
