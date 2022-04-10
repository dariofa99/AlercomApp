package com.alercom.app.response.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("access_token" ) var accessToken : String?                = null,
    @SerializedName("token_type"   ) var tokenType   : String?                = null,
    @SerializedName("expires_in"   ) var expiresIn   : Int?                   = null,
    @SerializedName("user"         ) var authUser    : AuthUser?              = AuthUser(),
    @SerializedName("permissions"  ) var permissions : ArrayList<Permissions> = arrayListOf()
)
