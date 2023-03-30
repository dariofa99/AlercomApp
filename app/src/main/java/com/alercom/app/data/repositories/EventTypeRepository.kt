package com.alercom.app.data.repositories


import com.alercom.app.data.model.EventType
import com.app.alercom.response.events.OnEventTypeResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.alercom.app.network.retrofit
import com.alercom.app.response.events.EventTypeResponse
import com.alercom.app.data.services.EventTypeService
import com.alercom.app.response.ErrorResponse


class EventTypeRepository {

    fun index(callback : OnEventTypeResponse) {

        val service = retrofit.create<EventTypeService>(EventTypeService::class.java)
        val call =  service.index()
        call.enqueue(object: Callback<List<EventType>> {
            override fun onResponse(call: Call<List<EventType>>, response: Response<List<EventType>>) {

                if(response.code() == 200){
                    callback.success(response.body())
                }
                if(response.code() == 403){
                    val error = ErrorResponse("Sesión expirada, vuelve a iniciar sesión")
                    callback.unautorize(error)

                }

            }

            override fun onFailure(call: Call<List<EventType>>, t: Throwable) {

            }


        })

    }

    fun getEventTypeByCategory(categoryId:Int,callback : OnEventTypeResponse) {

        val service = retrofit.create<EventTypeService>(EventTypeService::class.java)
        val call =  service.getEventTypeByCategory(categoryId)
        call.enqueue(object: Callback<EventTypeResponse> {

            override fun onResponse(
                call: Call<EventTypeResponse>,
                response: Response<EventTypeResponse>
            ) {
                if(response.code() == 200){
                    callback.success(response.body()?.eventTypes)
                }
            }

            override fun onFailure(call: Call<EventTypeResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })

    }
}