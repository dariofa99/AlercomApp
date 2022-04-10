package com.alercom.app.ui.alerts.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.EventType
import com.alercom.app.repositories.EventTypeRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.events.EventTypeResult
import com.app.alercom.response.events.OnEventTypeResponse

class EventViewModel (private val  eventTypeRepository: EventTypeRepository) : ViewModel() {

    private val _eventResult = MutableLiveData<EventTypeResult>()
    val eventResult: LiveData<EventTypeResult> = _eventResult

    fun index()  {

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
    }
}