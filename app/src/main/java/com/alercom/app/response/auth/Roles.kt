package com.alercom.app.response.auth

import com.google.gson.annotations.SerializedName


data class Roles (

    @SerializedName("id"          ) var id          : Int?                   = null,
    @SerializedName("name"        ) var name        : String?                = null,
    @SerializedName("guard_name"  ) var guardName   : String?                = null,
    @SerializedName("created_at"  ) var createdAt   : String?                = null,
    @SerializedName("updated_at"  ) var updatedAt   : String?                = null,
    @SerializedName("pivot"       ) var pivot       : Pivot?                 = Pivot(),
    @SerializedName("permissions" ) var permissions : ArrayList<Permissions> = arrayListOf()

)