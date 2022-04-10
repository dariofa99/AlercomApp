package com.alercom.app.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error" ) var error : String? = null
)
