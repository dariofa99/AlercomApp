package com.alercom.app.ui.alerts.create.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.EventType
import com.alercom.app.data.model.Reference
import com.alercom.app.repositories.EventTypeRepository
import com.alercom.app.repositories.ReferenceRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.events.EventTypeResult
import com.alercom.app.response.references.dynamic.DynamicReferenceResult
import com.alercom.app.response.references.dynamic.OnDynamicReferenceResponse
import com.app.alercom.response.events.OnEventTypeResponse

class EventCategoryViewModel (private val  eventTypeRepository: EventTypeRepository,
                              private val  referenceRepository: ReferenceRepository) : ViewModel() {

    private val _eventResult = MutableLiveData<EventTypeResult>()
    val eventResult: LiveData<EventTypeResult> = _eventResult

    private val _eventCategories = MutableLiveData<DynamicReferenceResult>()
    val eventCategories: LiveData<DynamicReferenceResult> = _eventCategories

   /* fun index()  {

        eventTypeRepository.index(object : OnEventTypeResponse {
            override fun success(eventType: List<EventType>?) {

                _eventResult.value = EventTypeResult(success = eventType)
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
    }*/

    fun getEventsCategories()  {

        referenceRepository.getEventsCategories(object : OnDynamicReferenceResponse {
            override fun success(reference: List<Reference>?) {
                System.out.println("Wiii")
                System.out.println("$reference")
                _eventCategories.value = DynamicReferenceResult(success = reference)
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