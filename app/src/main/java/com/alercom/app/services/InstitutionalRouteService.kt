package com.alercom.app.services


import com.alercom.app.response.institutional_routes.InstitutionalRouteResponse

import retrofit2.Call
import retrofit2.http.GET

interface InstitutionalRouteService {
    @GET("api/v1/institutional/routes")
    fun index(): Call<InstitutionalRouteResponse>
}