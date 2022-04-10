package com.alercom.app.services


import com.alercom.app.response.alerts.create.CreateAlert
import com.alercom.app.response.alerts.edit.EditAlertResponse
import com.alercom.app.response.alerts.list.ListAlertResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.*


interface AlertService {

    @Multipart
    @POST("api/v1/events")
    fun store(@PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part): Call<CreateAlert>
    @POST("api/v1/events")
    fun storeWithOuImage(@Body eventAlert: com.alercom.app.request.CreateAlert): Call<CreateAlert>
    @GET("api/v1/events")
    fun index(): Call<ListAlertResponse>

    @GET("api/v1/events/{alert_id}/edit")
    fun edit(@Path("alert_id") alert_id: Int?): Call<EditAlertResponse>
}