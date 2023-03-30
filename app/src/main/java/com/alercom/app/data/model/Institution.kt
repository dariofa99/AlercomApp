package com.alercom.app.data.model

import com.google.gson.annotations.SerializedName

data class Institution (
    @SerializedName("id"                  ) var id                 : Int?    = null,
    @SerializedName("institution_name"    ) var institutionName    : String? = null,
    @SerializedName("institution_address" ) var institutionAddress : String? = null,
    @SerializedName("institution_phone"   ) var institutionPhone   : String? = null,
    @SerializedName("town_id"             ) var townId             : Int?    = null,
    @SerializedName("created_at"          ) var createdAt          : String? = null,
    @SerializedName("updated_at"          ) var updatedAt          : String? = null,
    @SerializedName("town"                ) var town               : Town?   = Town()

)