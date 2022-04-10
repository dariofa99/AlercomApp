package com.alercom.app.services
import com.alercom.app.response.references.affectsranges.AffectsRangeResponse
import com.alercom.app.response.references.departments.DepartmentsResponse
import com.alercom.app.response.references.towns.TownResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReferenceService {
    @GET("api/v1/references/departments")
    fun getDepartments(): Call<DepartmentsResponse>
    @GET("api/v1/references/towns/{dep_id}")
    fun getTownByDeptId(@Path("dep_id") id: Int?): Call<TownResponse>
    @GET("api/v1/references/affects/range")
    fun getAffectsRange(): Call<AffectsRangeResponse>
}