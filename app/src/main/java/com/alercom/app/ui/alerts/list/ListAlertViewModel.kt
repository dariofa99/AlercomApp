package com.alercom.app.ui.alerts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.repositories.AlertRepository
import com.alercom.app.resources.paginator.Paginator
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.alerts.list.ListAlertResult
import com.alercom.app.response.alerts.list.OnListAlertResult


class ListAlertViewModel(private val eventReportRepository: AlertRepository) : ViewModel() {
    private  val _alertsResult = MutableLiveData<ListAlertResult>()
    val listAlertResult: LiveData<ListAlertResult> = _alertsResult

    fun index(data: String?, page: Int) {
        eventReportRepository.index(data,page,object : OnListAlertResult {
            override fun success(paginator: Paginator?) {
                _alertsResult?.value = ListAlertResult(success = paginator)
            }
            override fun unautorize(errorResponse: ErrorResponse) {
                _alertsResult?.value = ListAlertResult(unautorize = errorResponse)
            }
            override fun error(errorResponse: ErrorResponse) {
            }
            override fun errors(errors: ArrayList<String>?) {
            }
        })
    }
}