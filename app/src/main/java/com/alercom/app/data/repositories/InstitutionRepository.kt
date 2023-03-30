package com.alercom.app.data.repositories

import com.alercom.app.data.model.Institution
import com.alercom.app.data.model.InstitutionalRoute
import com.alercom.app.data.services.InstitutionService
import com.alercom.app.data.services.InstitutionalRouteService
import com.alercom.app.network.retrofit
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.institutional_routes.InstitutionalRouteResponse
import com.alercom.app.response.institutional_routes.OnInstitutionalRouteResponse
import com.alercom.app.response.institutions.InstitutionsResponse
import com.alercom.app.response.institutions.OnInstitutionsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstitutionRepository {
    fun index(callback : OnInstitutionsResponse) {
        val service = retrofit.create<InstitutionService>(InstitutionService::class.java)
        val call =  service.getInstitutions()
        call.enqueue(object: Callback<InstitutionsResponse> {
            override fun onResponse(
                call: Call<InstitutionsResponse>,
                response: Response<InstitutionsResponse>
            ) {

                if(response.code() == 200){
                    val institutionalRoute : ArrayList<Institution>? = response.body()?.institutions
                    callback.success(institutionalRoute)
                }

                if(response.code() == 403){
                    val error = ErrorResponse("Sesión expirada, vuelve a iniciar sesión")
                    callback.unautorize(error)

                }
            }
            override fun onFailure(call: Call<InstitutionsResponse>, t: Throwable) {

            }
        })
    }
}