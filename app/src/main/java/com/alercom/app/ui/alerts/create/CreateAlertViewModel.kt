package com.alercom.app.ui.alerts.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Alert
import com.alercom.app.data.model.EventType
import com.alercom.app.data.model.Reference
import com.alercom.app.data.repositories.AlertRepository
import com.alercom.app.data.repositories.EventTypeRepository
import com.alercom.app.data.repositories.ReferenceRepository
import com.alercom.app.request.CreateAlertRequest
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.alerts.create.OnUpdateAlertResult
import com.alercom.app.response.alerts.create.UpdateAlertResult


import com.alercom.app.response.events.EventTypeResult
import com.alercom.app.response.references.dynamic.DynamicReferenceResult
import com.alercom.app.response.references.statics.OnStaticReferenceResponse
import com.app.alercom.response.events.OnEventTypeResponse
import java.io.File


class CreateAlertViewModel (private val affectsRangeRepository: ReferenceRepository,
                            private val eventReportRepository: AlertRepository,
                            private val eventTypeRepository: EventTypeRepository
): ViewModel() {
    private val _rangesResult = MutableLiveData<DynamicReferenceResult>()
    val rangesResult: LiveData<DynamicReferenceResult> = _rangesResult

    private  val _eventReportResult = MutableLiveData<UpdateAlertResult>()
    val eventReportResult: LiveData<UpdateAlertResult> = _eventReportResult

    private  val _eventTypeResult = MutableLiveData<EventTypeResult>()
    val eventTypeResult: LiveData<EventTypeResult> = _eventTypeResult



    fun getAffectsRange()  {
        affectsRangeRepository.getAffectsRange(object : OnStaticReferenceResponse {
            override fun success(affectsRange: List<Reference>?) {
                _rangesResult?.value = DynamicReferenceResult(success = affectsRange)
            }
            override fun unautorize(errorResponse: ErrorResponse) {
            }
            override fun error(errorResponse: ErrorResponse) {
            }
            override fun errors(errors: ArrayList<String>?) {
            }
        })
    }

    fun getEventTypes(categoryId:Int)  {
        eventTypeRepository.getEventTypeByCategory(categoryId,object : OnEventTypeResponse {

            override fun success(eventType: List<EventType>?) {
                _eventTypeResult.value = EventTypeResult(success = eventType)
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