package com.alercom.app.response.user

import com.google.gson.annotations.SerializedName


data class Pivot (

  @SerializedName("model_id"   ) var modelId   : Int?    = null,
  @SerializedName("role_id"    ) var roleId    : Int?    = null,
  @SerializedName("model_type" ) var modelType : String? = null

)