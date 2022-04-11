package com.alercom.app.repositories

import com.alercom.app.data.model.Alert
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.alercom.app.network.retrofit
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.response.alerts.create.UpdateAlertResponse
import com.alercom.app.response.alerts.create.OnUpdateAlertResult
import com.alercom.app.response.alerts.edit.EditAlertResponse
import com.alercom.app.response.alerts.edit.OnEditAlertResult
import com.alercom.app.response.alerts.list.ListAlertResponse
import com.alercom.app.response.alerts.list.OnListAlertResult
import com.alercom.app.services.AlertService


class AlertRepository {

    fun index(callback : OnListAlertResult) {
        val service = retrofit.create<AlertService>(AlertService::class.java)
        val call =  service.index()
        call.enqueue(object: Callback<ListAlertResponse> {
            override fun onResponse(
                call: Call<ListAlertResponse>,
                response: Response<ListAlertResponse>
            ) {
                System.out.println("Aqui si ${response.body()?.alerts}")

                val alerts : ArrayList<Alert>? = response.body()?.alerts
                if(response.code() == 200){
                    System.out.println("Aqui si ${response.body()?.alerts}")
                    callback.success(alerts)
                }
            }
            override fun onFailure(call: Call<ListAlertResponse>, t: Throwable) {

            }
        })
    }



      fun store(eventReport: CreateAlertRequest, imageFile:File?, callback : OnUpdateAlertResult) {
          var call :Call<UpdateAlertResponse>
          val service = retrofit.create<AlertService>(AlertService::class.java)
          call = if(imageFile!=null){
              val requestBody : RequestBody = RequestBody.create(MediaType.parse("image/*"),imageFile)
              val map = setMapData(eventReport)
              val file = MultipartBody.Part.createFormData("image_event",imageFile?.name,requestBody)
              service.store(map,file)
          }else{
              service.storeWithOuImage(eventReport)
          }
        call.enqueue(object: Callback<UpdateAlertResponse> {
            override fun onResponse(call: Call<UpdateAlertResponse>, response: Response<UpdateAlertResponse>
            ) {
                if(response.code() == 200){
                   callback.success(response.body()?.event)
                }

                if(response.code() == 201){
                    callback.errors(response.body()?.errors)
                }
            }

            override fun onFailure(call: Call<UpdateAlertResponse>, t: Throwable) {
                System.out.println("por aqui")
               // callback.errors(response.body()?.errors)
            }
        })
    }

    fun edit(alertId: Int, callback: OnEditAlertResult) {
        val service = retrofit.create<AlertService>(AlertService::class.java)
        val call =  service.edit(alertId)
        call.enqueue(object: Callback<EditAlertResponse> {
            override fun onResponse(
                call: Call<EditAlertResponse>,
                response: Response<EditAlertResponse>
            ) {
                callback.success(response.body()?.alert)
            }

            override fun onFailure(call: Call<EditAlertResponse>, t: Throwable) {

            }

        })
    }

    fun update(id:Int,eventReport: CreateAlertRequest, imageFile:File?, callback : OnUpdateAlertResult) {
        var call :Call<UpdateAlertResponse>
        val service = retrofit.create<AlertService>(AlertService::class.java)
        System.out.println(eventReport)

        call = if(imageFile!=null){
            val requestBody : RequestBody = RequestBody.create(MediaType.parse("image/*"),imageFile)
            val map = setMapData(eventReport)
            val file = MultipartBody.Part.createFormData("image_event",imageFile?.name,requestBody)
            service.update(id,map,file)
        }else{
            service.updateWithOuImage(id,eventReport)
        }
        call.enqueue(object: Callback<UpdateAlertResponse> {
            override fun onResponse(call: Call<UpdateAlertResponse>, response: Response<UpdateAlertResponse>
            ) {
                if(response.code() == 200){
                    callback.success(response.body()?.event)
                }

                if(response.code() == 201){
                    callback.errors(response.body()?.errors)
                }
            }

            override fun onFailure(call: Call<UpdateAlertResponse>, t: Throwable) {
                System.out.println("por aqui")
                // callback.errors(response.body()?.errors)
            }
        })
    }

///////////////////////////////////////////////////////////////////////////////////////////////////
    private fun createPartFromString(string: String): RequestBody {
        val string = when (string) {
            "true" -> {"1"}
            "false" -> {"0"}
            else -> {string}
        }
        return RequestBody.create(
            okhttp3.MultipartBody.FORM , string
        )
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    private fun setMapData(eventReport:CreateAlertRequest): HashMap<String, RequestBody> {

        val map: HashMap<String, RequestBody> = HashMap()
        map["event_description"] = createPartFromString(eventReport?.eventDescription.toString())
        map["event_place"] = createPartFromString(eventReport?.eventPlace.toString())
        map["event_date"] = createPartFromString(eventReport?.eventDate.toString())
        map["town_id"] = createPartFromString(eventReport?.townId.toString())
        map["event_type_id"] = createPartFromString(eventReport?.eventTypeId.toString())
        map["afectations_range_id"] = createPartFromString(eventReport?.afectationsRangeId.toString())
        map["event_aditional_information"] = createPartFromString(eventReport?.eventAditionalInformation.toString())
        map["affected_people"] = createPartFromString(eventReport?.affectedPeople.toString())
        map["affected_family"] = createPartFromString(eventReport?.affectedFamily.toString())
        map["affected_animals"] = createPartFromString(eventReport?.affectedAnimals.toString())
        map["affected_infrastructure"] = createPartFromString(eventReport?.affectedInfrastructure.toString())
        map["affected_livelihoods"] = createPartFromString(eventReport?.affectedLivelihoods.toString())

        return map
    }
}

