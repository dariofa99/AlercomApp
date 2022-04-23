package com.alercom.app.response.institutional_routes

import com.alercom.app.data.model.InstitutionalRoute
import com.google.gson.annotations.SerializedName

data class InstitutionalRouteResponse(
    @SerializedName("institutional_routes" ) var institutionalRoute : ArrayList<InstitutionalRoute>  = arrayListOf(),
    @SerializedName("errors"              ) var errors             : ArrayList<String>   = arrayListOf()
)
