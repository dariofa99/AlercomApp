package com.alercom.app.response.auth

import com.google.gson.annotations.SerializedName


data class Pivot (

  @SerializedName("role_id"       ) var roleId       : Int? = null,
  @SerializedName("permission_id" ) var permissionId : Int? = null

)