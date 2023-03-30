package com.alercom.app.response.institutions

import com.alercom.app.data.model.Institution
import com.google.gson.annotations.SerializedName

data class InstitutionsResponse (
    @SerializedName("institutions" ) var institutions : ArrayList<Institution>  = arrayListOf(),
    @SerializedName("errors"              ) var errors             : ArrayList<String>   = arrayListOf()

)

