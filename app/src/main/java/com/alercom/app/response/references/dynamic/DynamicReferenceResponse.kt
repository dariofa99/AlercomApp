package com.alercom.app.response.references.dynamic



import com.alercom.app.data.model.Reference
import com.google.gson.annotations.SerializedName

data class DynamicReferenceResponse(
    @SerializedName("references"  ) var references  : ArrayList<Reference>  = arrayListOf(),
    @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()
)
