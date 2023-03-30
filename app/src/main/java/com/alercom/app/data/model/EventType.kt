package com.alercom.app.data.model

import com.google.gson.annotations.SerializedName

data class EventType(
    @SerializedName("id"                     ) var id                   : Int?    = null,
    @SerializedName("event_type_description" ) var eventTypeDescription : String? = null,
    @SerializedName("event_type_name"        ) var eventTypeName        : String? = null,
    @SerializedName("category_id"        ) var categoryId        : Int? = null,
    @SerializedName("category"        ) var category        : Reference? = null
){
    override fun toString(): String {
        return this.eventTypeName.toString()
    }
}
