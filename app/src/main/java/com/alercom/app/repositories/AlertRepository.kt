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
import com.alercom.app.response.alerts.create.CreateAlert
import com.alercom.app.response.alerts.create.OnCreateAlertResult
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



      fun store(eventReport: com.alercom.app.request.CreateAlert, imageFile:File? ,callback : OnCreateAlertResult) {
          var call :Call<CreateAlert>
          val service = retrofit.create<AlertService>(AlertService::class.java)
          if(imageFile!=null){
              val requestBody : RequestBody = RequestBody.create(MediaType.parse("image/*"),imageFile)
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
              val file = MultipartBody.Part.createFormData("image_event",imageFile?.name,requestBody)
              call =  service.store(map,file)
          }else{
              call =  service.storeWithOuImage(eventReport)
          }




        call.enqueue(object: Callback<CreateAlert> {
            override fun onResponse(call: Call<CreateAlert>, response: Response<CreateAlert>
            ) {
                if(response.code() == 200){
                   callback.success(response.body()?.event)
                }

                if(response.code() == 201){
                    callback.errors(response.body()?.errors)
                }
            }

            override fun onFailure(call: Call<CreateAlert>, t: Throwable) {
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
}

