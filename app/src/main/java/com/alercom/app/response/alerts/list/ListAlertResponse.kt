package com.alercom.app.response.alerts.list


import com.alercom.app.data.model.Alert
import com.google.gson.annotations.SerializedName

data class ListAlertResponse(
       @SerializedName("events" ) var events : ArrayList<Alert> = arrayListOf(),
       @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()

)
