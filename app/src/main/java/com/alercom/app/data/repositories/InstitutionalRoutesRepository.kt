package com.alercom.app.data.repositories

import com.alercom.app.data.model.InstitutionalRoute
import com.alercom.app.network.retrofit
import com.alercom.app.response.institutional_routes.InstitutionalRouteResponse
import com.alercom.app.response.institutional_routes.OnInstitutionalRouteResponse
import com.alercom.app.data.services.InstitutionalRouteService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstitutionalRoutesRepository {
    fun index(callback : OnInstitutionalRouteResponse) {
        val service = retrofit.create<InstitutionalRouteService>(InstitutionalRouteService::class.java)
        val call =  service.index()
        call.enqueue(object: Callback<InstitutionalRouteResponse> {

            override fun onResponse(
                call: Call<InstitutionalRouteResponse>,
                response: Response<InstitutionalRouteResponse>
            ) {

                if(response.code() == 200){
                    val institutionalRoute : ArrayList<InstitutionalRoute>? = response.body()?.institutionalRoute
                    callback.success(institutionalRoute)
                }
            }
            override fun onFailure(call: Call<InstitutionalRouteResponse>, t: Throwable) {

            }
        })
    }
}


