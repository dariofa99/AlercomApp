package com.app.alercom.response.towns

import com.alercom.app.data.model.Town
import com.alercom.app.response.ErrorResponse


interface OnTownResponse {
    fun success(townResponse: List<Town>)
    fun unautorize(errorResponse: ErrorResponse)
    fun error(errorResponse: ErrorResponse)
    fun errors(errors: ArrayList<String>? = arrayListOf())
}