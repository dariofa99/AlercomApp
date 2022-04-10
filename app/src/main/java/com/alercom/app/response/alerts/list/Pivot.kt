package com.alercom.app.response.alerts.list

import com.google.gson.annotations.SerializedName

data class Pivot(
    @SerializedName("event_id"   ) var eventId   : Int?    = null,
    @SerializedName("file_id"    ) var fileId    : Int?    = null,
    @SerializedName("id"         ) var id        : Int?    = null,
    @SerializedName("user_id"    ) var userId    : Int?    = null,
    @SerializedName("status_id"  ) var statusId  : Int?    = null,
    @SerializedName("type_id"    ) var typeId    : Int?    = null,
    @SerializedName("created_at" ) var createdAt : String? = null,
    @SerializedName("updated_at" ) var updatedAt : String? = null
)
