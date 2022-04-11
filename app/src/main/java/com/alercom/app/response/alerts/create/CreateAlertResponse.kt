package com.alercom.app.response.alerts.create


import com.alercom.app.data.model.Alert
import com.google.gson.annotations.SerializedName

data class CreateAlertResponse(
       @SerializedName("alert"  ) var event  : Alert? = Alert(),
       @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()

)
