package com.alercom.app.data.services


import com.alercom.app.data.model.EventType
import com.alercom.app.response.events.EventTypeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EventTypeService {
    @GET("api/v1/event/types")
    fun index(): Call<List<EventType>>

    @GET("api/v1/event/category/types/{id}")
    fun getEventTypeByCategory(@Path("id") categoryId:Int): Call<EventTypeResponse>
}