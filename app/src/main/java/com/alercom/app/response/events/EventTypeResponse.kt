package com.alercom.app.response.events

import com.alercom.app.data.model.EventType
import com.google.gson.annotations.SerializedName

data class EventTypeResponse(
    @SerializedName("event_types"  ) var eventTypes  : ArrayList<EventType>  = arrayListOf(),
    @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()
) {
}