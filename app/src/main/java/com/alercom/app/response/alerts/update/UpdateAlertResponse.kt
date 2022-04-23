package com.alercom.app.response.alerts.update


import com.alercom.app.data.model.Alert
import com.google.gson.annotations.SerializedName

data class UpdateAlertResponse(
       @SerializedName("alert"  ) var event  : Alert? = Alert(),
       @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()

)
