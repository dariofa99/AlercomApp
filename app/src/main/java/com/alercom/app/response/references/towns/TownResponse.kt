package com.alercom.app.response.references.towns

import com.alercom.app.data.model.Town
import com.google.gson.annotations.SerializedName

data class TownResponse(
    @SerializedName("towns"  ) var towns  : ArrayList<Town>  = arrayListOf(),
    @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()
)
