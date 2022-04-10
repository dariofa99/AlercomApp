package com.alercom.app.services


import com.alercom.app.data.model.EventType
import retrofit2.Call
import retrofit2.http.GET

interface EventTypeService {
    @GET("api/v1/event/types")
    fun index(): Call<List<EventType>>
}