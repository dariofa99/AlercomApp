package com.alercom.app.ui.use

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Institution
import com.alercom.app.data.model.Reference
import com.alercom.app.data.repositories.InstitutionRepository
import com.alercom.app.data.repositories.ReferenceRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.institutions.InstitutionsResult
import com.alercom.app.response.institutions.OnInstitutionsResponse
import com.alercom.app.response.references.dynamic.DynamicReferenceResult
import com.alercom.app.response.references.dynamic.OnDynamicReferenceResponse
import com.alercom.app.response.references.statics.OnStaticReferenceResponse


class AlercomUseViewModel(private val institutionRepository: InstitutionRepository,
                          private val  referenceRepository: ReferenceRepository
) : ViewModel() {
    private val _institutionsResult = MutableLiveData<InstitutionsResult>()
    val institutionsResult: LiveData<InstitutionsResult> = _institutionsResult
    private val _eventCategories = MutableLiveData<DynamicReferenceResult>()
    val eventCategories: LiveData<DynamicReferenceResult> = _eventCategories

    fun getInstitutions()  {
        institutionRepository.index(object : OnInstitutionsResponse {
            override fun success(institution: List<Institution>?) {
                _institutionsResult?.value = InstitutionsResult(success = institution)
            }
            override fun unautorize(errorResponse: ErrorResponse) {
            }
            override fun error(errorResponse: ErrorResponse) {
            }
            override fun errors(errors: ArrayList<String>?) {
            }
        })
    }

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