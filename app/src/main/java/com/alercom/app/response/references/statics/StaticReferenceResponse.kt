package com.alercom.app.response.references.statics


import com.alercom.app.data.model.Reference
import com.google.gson.annotations.SerializedName

data class StaticReferenceResponse(
    @SerializedName("references"  ) var references  : ArrayList<Reference>  = arrayListOf(),
    @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()
)
