package com.alercom.app.data.repositories



import com.alercom.app.data.model.Reference
import com.alercom.app.data.model.Town
import com.alercom.app.response.references.statics.OnStaticReferenceResponse
import com.app.alercom.response.towns.OnTownResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.alercom.app.network.retrofit
import com.alercom.app.response.references.dynamic.DynamicReferenceResponse
import com.alercom.app.response.references.dynamic.OnDynamicReferenceResponse
import com.alercom.app.response.references.statics.StaticReferenceResponse
import com.alercom.app.response.references.towns.TownResponse
import com.alercom.app.data.services.ReferenceService


class ReferenceRepository {
    fun getDepartments(callback : OnStaticReferenceResponse) {

        val service = retrofit.create<ReferenceService>(ReferenceService::class.java)
        val call =  service.getDepartments()
        call.enqueue(object: Callback<StaticReferenceResponse> {
            override fun onResponse(
                call: Call<StaticReferenceResponse>,
                response: Response<StaticReferenceResponse>
            ) {
                val reference : ArrayList<Reference>? = response.body()?.references
                if(response.code() == 200){
                    callback.success(reference)
                }
            }
            override fun onFailure(call: Call<StaticReferenceResponse>, t: Throwable) {

            }
        })
    }

    fun getTownsByDepId(dep_id:Int,callback : OnTownResponse) {
        val service = retrofit.create<ReferenceService>(ReferenceService::class.java)
        val call =  service.getTownByDeptId(dep_id)
        call.enqueue(object: Callback<TownResponse> {
            override fun onResponse(
                call: Call<TownResponse>,
                response: Response<TownResponse>
            ) {
                val towns : ArrayList<Town>? = response.body()?.towns
                if(response.code() == 200){
                    callback.success(towns!!)
                }
            }
            override fun onFailure(call: Call<TownResponse>, t: Throwable) {

            }
        })
    }

    fun getAffectsRange(callback : OnStaticReferenceResponse) {

        val service = retrofit.create<ReferenceService>(ReferenceService::class.java)
        val call =  service.getAffectsRange()
        call.enqueue(object: Callback<StaticReferenceResponse> {
            override fun onResponse(
                call: Call<StaticReferenceResponse>,
                response: Response<StaticReferenceResponse>
            ) {
                val reference : ArrayList<Reference>? = response.body()?.references
                if(response.code() == 200){
                    callback.success(reference)
                }
            }
            override fun onFailure(call: Call<StaticReferenceResponse>, t: Throwable) {

            }
        })
    }

    fun getEventsCategories(callback : OnDynamicReferenceResponse) {
        val service = retrofit.create<ReferenceService>(ReferenceService::class.java)
        val call =  service.getEventsCategories()
        call.enqueue(object: Callback<DynamicReferenceResponse> {
            override fun onResponse(
                call: Call<DynamicReferenceResponse>,
                response: Response<DynamicReferenceResponse>
            ) {
                val reference : ArrayList<Reference>? = response.body()?.references
                if(response.code() == 200){
                    callback.success(reference)
                }
            }
            override fun onFailure(call: Call<DynamicReferenceResponse>, t: Throwable) {

            }
        })
    }

}