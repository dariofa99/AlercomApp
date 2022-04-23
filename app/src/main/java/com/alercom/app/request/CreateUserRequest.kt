package com.alercom.app.request

import com.google.gson.annotations.SerializedName

data class CreateUserRequest(
    @SerializedName("name"              ) var name: String? = null,
    @SerializedName("lastname"          ) var lastname: String? = null,
    @SerializedName("username"          ) var username: String?          = null,
    @SerializedName("email"             ) var email: String?          = null,
    @SerializedName("phone_number"      ) var phoneNumber: String?          = null,
    @SerializedName("password"          ) var password: String?          = null,
    @SerializedName("oldpassword"       ) var oldPassword: String?          = null,
    @SerializedName("password_confirmation"    ) var confirmpassword: String?          = null,


) {
}