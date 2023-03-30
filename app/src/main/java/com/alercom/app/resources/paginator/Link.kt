package com.alercom.app.resources.paginator

import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("url"    ) var url    : String?  = null,
    @SerializedName("label"  ) var label  : String?  = null,
    @SerializedName("active" ) var active : Boolean? = null

)
