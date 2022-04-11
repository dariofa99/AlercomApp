package com.alercom.app.ui.alerts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Alert
import com.alercom.app.data.model.Reference
import com.alercom.app.repositories.AlertRepository
import com.alercom.app.repositories.ReferenceRepository
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.alerts.create.UpdateAlertResult
import com.alercom.app.response.alerts.create.OnUpdateAlertResult
import com.alercom.app.response.references.affectsranges.AffectsRangeResult
import com.alercom.app.response.references.affectsranges.OnAffectsRangeResponse
import java.io.File


class CreateAlertViewModel (private val affectsRangeRepository: ReferenceRepository,
                            private val eventReportRepository: AlertRepository
): ViewModel() {
    private val _rangesResult = MutableLiveData<AffectsRangeResult>()
    val rangesResult: LiveData<AffectsRangeResult> = _rangesResult

    private  val _eventReportResult = MutableLiveData<UpdateAlertResult>()
    val eventReportResult: LiveData<UpdateAlertResult> = _eventReportResult



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

    fun store(newAlertRequest: CreateAlertRequest, file: File?) {

        eventReportRepository.store(newAlertRequest,file, object : OnUpdateAlertResult {
            override fun success(eventReport: Alert?) {
                _eventReportResult?.value = UpdateAlertResult(success = eventReport)
            }
            override fun unautorize(errorResponse: ErrorResponse) {

            }
            override fun error(errorResponse: ErrorResponse) {
            }

            override fun errors(errors: ArrayList<String>?) {
                _eventReportResult?.value = UpdateAlertResult(errors = errors)
                System.out.println(errors)
            }
        })
    }
}