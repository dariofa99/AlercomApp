package com.alercom.app.services


import com.alercom.app.request.CreateAlertRequest

import com.alercom.app.response.alerts.edit.EditAlertResponse
import com.alercom.app.response.alerts.list.ListAlertResponse
import com.alercom.app.response.alerts.update.UpdateAlertResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.*


interface AlertService {

    @Multipart
    @POST("api/v1/events")
    fun store(@PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part): Call<UpdateAlertResponse>
    @POST("api/v1/events")
    fun storeWithOuImage(@Body eventAlertRequest: CreateAlertRequest): Call<UpdateAlertResponse>
    @GET("api/v1/events")
    fun index(@Query("data") data:String): Call<ListAlertResponse>

    @GET("api/v1/events/{alert_id}/edit")
    fun edit(@Path("alert_id") alert_id: Int?): Call<EditAlertResponse>

    @Multipart
    @POST("api/v1/events/{id}")
    fun update(@Path("id") id: Int?,@PartMap map: HashMap<String, RequestBody>, @Part image: MultipartBody.Part): Call<UpdateAlertResponse>
    @POST("api/v1/events/{id}")
    fun updateWithOuImage(@Path("id") id: Int?,@Body eventAlertRequest: CreateAlertRequest): Call<UpdateAlertResponse>

}