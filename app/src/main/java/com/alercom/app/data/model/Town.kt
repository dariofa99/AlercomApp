package com.alercom.app.data.model

import com.google.gson.annotations.SerializedName

data class Town(
    @SerializedName("id"            ) var id           : Int?        = null,
    @SerializedName("town_name"     ) var townName     : String?     = null,
    @SerializedName("department_id" ) var departmentId : Int?        = null,
    @SerializedName("department"    ) var department   : Reference? = Reference()
){
    override fun toString(): String {
        return this.townName.toString()
    }
}
