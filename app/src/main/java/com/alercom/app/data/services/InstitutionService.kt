package com.alercom.app.data.services


import com.alercom.app.response.institutions.InstitutionsResponse
import retrofit2.Call
import retrofit2.http.GET

interface InstitutionService {
    @GET("api/v1/institutions/information/")
    fun getInstitutions(): Call<InstitutionsResponse>
}