package com.alercom.app.data.model

import com.google.gson.annotations.SerializedName

data class Reference(
    @SerializedName("id"             ) var id            : Int?    = null,
    @SerializedName("reference_name" ) var referenceName : String? = null,
    @SerializedName("reference_description" ) var referenceDescription : String? = null,
    @SerializedName("category"       ) var category      : String? = null,
    @SerializedName("section"       ) var  section       : String? = null,
    @SerializedName("is_active"      ) var isActive      : Int?    = null

){
    override fun toString(): String {
        return this.referenceName.toString()
    }
}