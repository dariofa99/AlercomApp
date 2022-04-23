package com.alercom.app.ui.alerts.show

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Alert
import com.alercom.app.repositories.AlertRepository
import com.alercom.app.repositories.EventTypeRepository
import com.alercom.app.repositories.ReferenceRepository
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.alerts.create.OnUpdateAlertResult
import com.alercom.app.response.alerts.create.UpdateAlertResult
import com.alercom.app.response.alerts.edit.EditAlertResult
import com.alercom.app.response.alerts.edit.OnEditAlertResult
import java.io.File

class ShowEventViewModel(private val affectsRangeRepository: ReferenceRepository,
                         private val alertRepository: AlertRepository,
                         private val eventTypeRepository: EventTypeRepository
) : ViewModel() {
    private val _eventResult = MutableLiveData<EditAlertResult>()
    val eventResult: LiveData<EditAlertResult> = _eventResult
    private val _alertUpdateResult = MutableLiveData<UpdateAlertResult>()
    val alertUpdateResult: LiveData<UpdateAlertResult> = _alertUpdateResult

    fun edit(alertId:Int)  {

        alertRepository.edit(alertId = alertId,object : OnEditAlertResult {
            override fun success(alert: Alert?) {
                _eventResult.value = EditAlertResult(success = alert)
            }


            override fun unautorize(unautorize: ErrorResponse) {
                //_userResult.value = UserResult(unautorize = unautorize)
            }

            override fun error(auth: ErrorResponse) {

            }

            override fun errors(errors: ArrayList<String>?) {
                //_userResult.value = UserResult(errors = errors)
            }

        })
    }

    fun update(id:Int, newAlert: CreateAlertRequest, file: File?) {

        alertRepository.update(id,newAlert,file, object : OnUpdateAlertResult {
            override fun success(eventReport: Alert?) {
                _alertUpdateResult?.value = UpdateAlertResult(success = eventReport)
            }
            override fun unautorize(errorResponse: ErrorResponse) {

            }
            override fun error(errorResponse: ErrorResponse) {
            }

            override fun errors(errors: ArrayList<String>?) {
                _alertUpdateResult?.value = UpdateAlertResult(errors = errors)

            }
        })
    }
}