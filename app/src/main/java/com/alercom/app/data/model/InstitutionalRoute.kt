package com.alercom.app.data.model

import com.google.gson.annotations.SerializedName

data class InstitutionalRoute (

    @SerializedName("route_name" ) var routeName : String? = null,
    @SerializedName("route_url"  ) var routeUrl  : String? = null,
    @SerializedName("id"         ) var id        : Int?    = null

)
