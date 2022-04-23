package com.alercom.app.ui.institutional_routes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.InstitutionalRoute
import com.alercom.app.data.model.User
import com.alercom.app.repositories.InstitutionalRoutesRepository

import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.institutional_routes.InstitutionalRouteResult
import com.alercom.app.response.institutional_routes.OnInstitutionalRouteResponse
import com.alercom.app.response.user.OnUserResponse
import com.alercom.app.response.user.UserResult

class InstitutionalRouteViewModel(private val institutionalRoutesRepository: InstitutionalRoutesRepository) : ViewModel() {

    private val _institutionalRouteResult = MutableLiveData<InstitutionalRouteResult>()
    val institutionalRouteResult: LiveData<InstitutionalRouteResult> = _institutionalRouteResult

    fun index()  {
        institutionalRoutesRepository.index(object : OnInstitutionalRouteResponse {
            override fun success(institutionalRoute: List<InstitutionalRoute>?) {
                _institutionalRouteResult.value = InstitutionalRouteResult(success = institutionalRoute)
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