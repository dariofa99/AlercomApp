package com.alercom.app.response.alerts.list



import com.alercom.app.resources.paginator.Paginator
import com.google.gson.annotations.SerializedName

data class ListAlertResponse(
       @SerializedName("paginator" ) var paginator : Paginator = Paginator(),
       @SerializedName("errors" ) var errors : ArrayList<String> = arrayListOf()

)
