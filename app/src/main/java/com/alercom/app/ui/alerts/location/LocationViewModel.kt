package com.alercom.app.ui.alerts.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Reference
import com.alercom.app.data.model.Town
import com.alercom.app.repositories.ReferenceRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.references.departments.DepartmentsResult
import com.alercom.app.response.references.departments.OnDepartmentsResponse
import com.alercom.app.response.references.towns.TownResult
import com.app.alercom.response.towns.OnTownResponse

class LocationViewModel(private val referenceRepository: ReferenceRepository) : ViewModel() {

    private val _referenceResult = MutableLiveData<DepartmentsResult>()
    val departmentsResult: LiveData<DepartmentsResult> = _referenceResult

    private val _townsResult = MutableLiveData<TownResult>()
    val townsResult: LiveData<TownResult> = _townsResult

    fun getDepartments()  {

        referenceRepository.getDepartments(object : OnDepartmentsResponse {
            override fun successList(reference: List<Reference>?) {
                _referenceResult?.value = DepartmentsResult(successList = reference)
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