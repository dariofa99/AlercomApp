package com.alercom.app.response.user

import com.alercom.app.data.model.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user"   ) var user   : User?             = User(),
    @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()
)
