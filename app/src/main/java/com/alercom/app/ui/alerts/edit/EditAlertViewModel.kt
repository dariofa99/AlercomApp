package com.alercom.app.ui.alerts.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Alert
import com.alercom.app.data.model.Reference
import com.alercom.app.repositories.AlertRepository
import com.alercom.app.repositories.ReferenceRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.alerts.edit.EditAlertResult
import com.alercom.app.response.alerts.edit.OnEditAlertResult
import com.alercom.app.response.references.affectsranges.AffectsRangeResult
import com.alercom.app.response.references.affectsranges.OnAffectsRangeResponse

class EditAlertViewModel(private val affectsRangeRepository: ReferenceRepository,
                         private val alertRepository: AlertRepository) : ViewModel() {
    private val _eventResult = MutableLiveData<EditAlertResult>()
    val eventResult: LiveData<EditAlertResult> = _eventResult

    private val _rangesResult = MutableLiveData<AffectsRangeResult>()
    val rangesResult: LiveData<AffectsRangeResult> = _rangesResult

    fun getAffectsRange()  {
        affectsRangeRepository.getAffectsRange(object : OnAffectsRangeResponse {
            override fun success(affectsRange: List<Reference>?) {
                _rangesResult?.value = AffectsRangeResult(success = affectsRange)
            }
            override fun unautorize(errorResponse: ErrorResponse) {
            }
            override fun error(errorResponse: ErrorResponse) {
            }
            override fun errors(errors: ArrayList<String>?) {
            }
        })
    }

    fun index(alertId:Int)  {

        alertRepository.edit(alertId = alertId,object : OnEditAlertResult {
            override fun success(alert: Alert?) {
                System.out.println("Aaui estoy sin saber que hacer")
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
}