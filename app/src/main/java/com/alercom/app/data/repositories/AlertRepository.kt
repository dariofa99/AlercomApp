package com.alercom.app.data.repositories

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import com.alercom.app.network.retrofit
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.data.services.AlertService
import com.alercom.app.resources.paginator.Paginator
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.alerts.create.OnUpdateAlertResult
import com.alercom.app.response.alerts.edit.EditAlertResponse
import com.alercom.app.response.alerts.edit.OnEditAlertResult
import com.alercom.app.response.alerts.list.ListAlertResponse
import com.alercom.app.response.alerts.list.OnListAlertResult
import com.alercom.app.response.alerts.update.UpdateAlertResponse


class AlertRepository {

    fun index(data: String?, page: Int, callback: OnListAlertResult) {
        val service = retrofit.create<AlertService>(AlertService::class.java)
        val call =  service.index(data!!,page)
        call.enqueue(object: Callback<Paginator> {
            override fun onResponse(
                call: Call<Paginator>,
                response: Response<Paginator>
            ) {



                if(response.code() == 200){
                    val paginator : Paginator = response.body()!!
                    callback.success(paginator)
                }
                if(response.code() == 403){
                    val error = ErrorResponse("Sesión expirada, vuelve a ingresar")
                    callback.unautorize(error)

                }
            }
            override fun onFailure(call: Call<Paginator>, t: Throwable) {

            }
        })
    }



      fun store(eventReport: CreateAlertRequest, imageFile:File?, callback : OnUpdateAlertResult) {
          val call :Call<UpdateAlertResponse>
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

                if(response.code() == 200){
                    callback.success(response.body()?.event)
                }

                if(response.code() == 403){
                    val error = ErrorResponse("Sesión expirada, vuelve a iniciar sesión")
                    callback.unautorize(error)

                }
            }

            override fun onFailure(call: Call<EditAlertResponse>, t: Throwable) {

            }

        })
    }

    fun delete(alertId: Int, callback: OnUpdateAlertResult) {
        val service = retrofit.create<AlertService>(AlertService::class.java)
        val call =  service.delete(alertId)
        call.enqueue(object: Callback<UpdateAlertResponse> {
            override fun onResponse(
                call: Call<UpdateAlertResponse>,
                response: Response<UpdateAlertResponse>
            ) {

                if(response.code() == 200){
                    callback.success(response.body()?.event)
                }

                if(response.code() == 403){
                    val error = ErrorResponse("Sesión expirada, vuelve a iniciar sesión")
                    callback.unautorize(error)

                }
            }

            override fun onFailure(call: Call<UpdateAlertResponse>, t: Throwable) {

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
                System.out.println("Joder ${response.body()}")
                if(response.code() == 200){
                    System.out.println("Joder ${response.body()?.event}")
                    callback.success(response.body()?.event)
                }

                if(response.code() == 201){
                    callback.errors(response.body()?.errors)
                }
            }

            override fun onFailure(call: Call<UpdateAlertResponse>, t: Throwable) {

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
        map["latitude"] = createPartFromString(eventReport?.latitude.toString())
        map["longitude"] = createPartFromString(eventReport?.longitude.toString())

        return map
    }
}

