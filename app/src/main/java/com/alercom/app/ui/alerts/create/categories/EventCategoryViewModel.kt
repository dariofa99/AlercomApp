package com.alercom.app.ui.alerts.create.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Reference
import com.alercom.app.data.repositories.EventTypeRepository
import com.alercom.app.data.repositories.ReferenceRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.events.EventTypeResult
import com.alercom.app.response.references.dynamic.DynamicReferenceResult
import com.alercom.app.response.references.dynamic.OnDynamicReferenceResponse

class EventCategoryViewModel (private val  referenceRepository: ReferenceRepository) : ViewModel() {

    private val _eventResult = MutableLiveData<EventTypeResult>()
    val eventResult: LiveData<EventTypeResult> = _eventResult

    private val _eventCategories = MutableLiveData<DynamicReferenceResult>()
    val eventCategories: LiveData<DynamicReferenceResult> = _eventCategories

    fun getEventsCategories()  {

        referenceRepository.getEventsCategories(object : OnDynamicReferenceResponse {
            override fun success(reference: List<Reference>?) {
                 _eventCategories.value = DynamicReferenceResult(success = reference)
            }


            override fun unautorize(unautorize: ErrorResponse) {
                _eventCategories.value = DynamicReferenceResult(unautorize = unautorize)
            }

            override fun error(auth: ErrorResponse) {

            }

            override fun errors(errors: ArrayList<String>?) {
                //_userResult.value = UserResult(errors = errors)
            }

        })
    }

}