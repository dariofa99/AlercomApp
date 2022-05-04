package com.alercom.app.data.model.auth

import com.alercom.app.response.auth.Roles
import com.google.gson.annotations.SerializedName
import com.alercom.app.network.Prefs

class Auth(
    @SerializedName("id"                ) var id              : Int?             = null,
    @SerializedName("name"              ) var name            : String?          = null,
    @SerializedName("lastname"          ) var lastname        : String?          = null,
    @SerializedName("username"          ) var username        : String?          = null,
    @SerializedName("email"             ) var email           : String?          = null,
    @SerializedName("phone_number"      ) var phoneNumber     : String?          = null,
    @SerializedName("address"           ) var address         : String?          = null,
    @SerializedName("email_verified_at" ) var emailVerifiedAt : String?          = null,
    @SerializedName("town_id"           ) var townId          : Int?             = null,
    @SerializedName("status_id"         ) var statusId        : Int?             = null,
    @SerializedName("created_at"        ) var createdAt       : String?          = null,
    @SerializedName("updated_at"        ) var updatedAt       : String?          = null,
    @SerializedName("permissions"       ) var permissions     : ArrayList<String> = arrayListOf(),
    @SerializedName("roles"             ) var roles           : ArrayList<Roles>  = arrayListOf())
{

}