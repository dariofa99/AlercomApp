package com.alercom.app.response.references.departments


import com.alercom.app.data.model.Reference
import com.google.gson.annotations.SerializedName

data class DepartmentsResponse(
    @SerializedName("departments"  ) var departments  : ArrayList<Reference>  = arrayListOf(),
    @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()
)
