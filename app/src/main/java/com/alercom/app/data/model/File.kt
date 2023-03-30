package com.alercom.app.data.model


import com.alercom.app.response.alerts.list.Pivot
import com.google.gson.annotations.SerializedName

data class File(

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("original_name" ) var originalName : String? = null,
    @SerializedName("encrypt_name"  ) var encryptName  : String? = null,
    @SerializedName("path"          ) var path         : String? = null,
    @SerializedName("size"          ) var size         : String? = null,
    @SerializedName("real_path"     ) var realPath     : String? = null,
    @SerializedName("pivot"         ) var pivot        : Pivot?  = Pivot()

)

