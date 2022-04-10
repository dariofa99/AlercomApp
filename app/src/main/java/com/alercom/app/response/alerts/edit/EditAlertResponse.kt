package com.alercom.app.response.alerts.edit


import com.alercom.app.data.model.Alert
import com.alercom.app.data.model.Reference
import com.google.gson.annotations.SerializedName

data class EditAlertResponse(
       @SerializedName("alert" ) var alert : Alert = Alert(),
       @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf(),
       @SerializedName("ranges") var ranges  : ArrayList<Reference>  = arrayListOf(),

)
