package com.alercom.app.repositories


import com.alercom.app.data.model.EventType
import com.app.alercom.response.events.OnEventTypeResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.alercom.app.network.retrofit
import com.alercom.app.services.EventTypeService


class EventTypeRepository {

    fun index(callback : OnEventTypeResponse) {

        val service = retrofit.create<EventTypeService>(EventTypeService::class.java)
        val call =  service.index()
        call.enqueue(object: Callback<List<EventType>> {
            override fun onResponse(call: Call<List<EventType>>, response: Response<List<EventType>>) {

                if(response.code() == 200){
                    callback.success(response.body())
                }

            }

            override fun onFailure(call: Call<List<EventType>>, t: Throwable) {

            }


        })

    }
}