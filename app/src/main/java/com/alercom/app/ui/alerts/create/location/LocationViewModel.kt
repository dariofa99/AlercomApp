package com.alercom.app.ui.alerts.create.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Reference
import com.alercom.app.data.model.Town
import com.alercom.app.repositories.ReferenceRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.references.statics.StaticReferenceResult
import com.alercom.app.response.references.statics.OnStaticReferenceResponse
import com.alercom.app.response.references.towns.TownResult
import com.app.alercom.response.towns.OnTownResponse

class LocationViewModel(private val referenceRepository: ReferenceRepository) : ViewModel() {

    private val _referenceResult = MutableLiveData<StaticReferenceResult>()
    val staticReferenceResult: LiveData<StaticReferenceResult> = _referenceResult

    private val _townsResult = MutableLiveData<TownResult>()
    val townsResult: LiveData<TownResult> = _townsResult

    fun getDepartments()  {

        referenceRepository.getDepartments(object : OnStaticReferenceResponse {
            override fun success(reference: List<Reference>?) {
                _referenceResult?.value = StaticReferenceResult(success = reference)
            }

            override fun unautorize(errorResponse: ErrorResponse) {

            }

            override fun error(errorResponse: ErrorResponse) {

            }

            override fun errors(errors: ArrayList<String>?) {

            }


        })
    }

    fun getTownByDepId(id:Int)  {

        referenceRepository.getTownsByDepId(id,object : OnTownResponse {
            override fun success(town: List<Town>) {
                _townsResult?.value = TownResult(success = town)
            }

            override fun unautorize(errorResponse: ErrorResponse) {

            }

            override fun error(errorResponse: ErrorResponse) {

            }

            override fun errors(errors: ArrayList<String>?) {

            }


        })
    }

}