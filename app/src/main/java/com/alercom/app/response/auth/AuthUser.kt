package com.alercom.app.response.auth


import com.google.gson.annotations.SerializedName

data class AuthUser(
    @SerializedName("id"                ) var id              : Int?              = null,
    @SerializedName("name"              ) var name            : String?           = null,
    @SerializedName("lastname"          ) var lastname        : String?           = null,
    @SerializedName("username"          ) var username        : String?           = null,
    @SerializedName("email"             ) var email           : String?           = null,
    @SerializedName("email_verified_at" ) var emailVerifiedAt : String?           = null,
    @SerializedName("created_at"        ) var createdAt       : String?           = null,
    @SerializedName("updated_at"        ) var updatedAt       : String?           = null,
    @SerializedName("permissions"       ) var permissions     : ArrayList<String> = arrayListOf(),
    @SerializedName("roles"             ) var roles           : ArrayList<Roles>  = arrayListOf()
)
