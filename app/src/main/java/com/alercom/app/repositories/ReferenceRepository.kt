package com.alercom.app.repositories



import com.alercom.app.data.model.Reference
import com.alercom.app.data.model.Town
import com.alercom.app.response.references.departments.OnDepartmentsResponse
import com.app.alercom.response.towns.OnTownResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.alercom.app.network.retrofit
import com.alercom.app.response.references.affectsranges.AffectsRangeResponse
import com.alercom.app.response.references.affectsranges.OnAffectsRangeResponse
import com.alercom.app.response.references.departments.DepartmentsResponse
import com.alercom.app.response.references.towns.TownResponse
import com.alercom.app.services.ReferenceService


class ReferenceRepository {
    fun getDepartments(callback : OnDepartmentsResponse) {

        val service = retrofit.create<ReferenceService>(ReferenceService::class.java)
        val call =  service.getDepartments()
        call.enqueue(object: Callback<DepartmentsResponse> {
            override fun onResponse(
                call: Call<DepartmentsResponse>,
                response: Response<DepartmentsResponse>
            ) {
                val reference : ArrayList<Reference>? = response.body()?.departments
                if(response.code() == 200){
                    callback.successList(reference)
                }
            }
            override fun onFailure(call: Call<DepartmentsResponse>, t: Throwable) {

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

    fun getAffectsRange(callback : OnAffectsRangeResponse) {

        val service = retrofit.create<ReferenceService>(ReferenceService::class.java)
        val call =  service.getAffectsRange()
        call.enqueue(object: Callback<AffectsRangeResponse> {
            override fun onResponse(
                call: Call<AffectsRangeResponse>,
                response: Response<AffectsRangeResponse>
            ) {
                val reference : ArrayList<Reference>? = response.body()?.ranges
                if(response.code() == 200){
                    callback.success(reference)
                }
            }
            override fun onFailure(call: Call<AffectsRangeResponse>, t: Throwable) {

            }
        })
    }

}