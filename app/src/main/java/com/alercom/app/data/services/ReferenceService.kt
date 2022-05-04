package com.alercom.app.data.services
import com.alercom.app.response.references.dynamic.DynamicReferenceResponse
import com.alercom.app.response.references.statics.StaticReferenceResponse
import com.alercom.app.response.references.towns.TownResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReferenceService {
    @GET("api/v1/references/departments")
    fun getDepartments(): Call<StaticReferenceResponse>
    @GET("api/v1/references/towns/{dep_id}")
    fun getTownByDeptId(@Path("dep_id") id: Int?): Call<TownResponse>
    @GET("api/v1/references/affects/range")
    fun getAffectsRange(): Call<StaticReferenceResponse>
    @GET("api/v1/references/event/categories")
    fun getEventsCategories(): Call<DynamicReferenceResponse>
}