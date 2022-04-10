package com.alercom.app.response.references.affectsranges



import com.alercom.app.data.model.Reference
import com.google.gson.annotations.SerializedName

data class AffectsRangeResponse(
    @SerializedName("ranges"  ) var ranges  : ArrayList<Reference>  = arrayListOf(),
    @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()
)
