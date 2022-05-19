package com.alercom.app.ui.alerts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alercom.app.data.model.Alert
import com.alercom.app.data.repositories.AlertRepository
import com.alercom.app.response.ErrorResponse
import com.alercom.app.response.alerts.list.ListAlertResult
import com.alercom.app.response.alerts.list.OnListAlertResult

class ListAlertViewModel(private val eventReportRepository: AlertRepository) : ViewModel() {
    private  val _alertsResult = MutableLiveData<ListAlertResult>()
    val listAlertResult: LiveData<ListAlertResult> = _alertsResult

    fun index(data: String?) {
        eventReportRepository.index(data,object : OnListAlertResult {
            override fun success(alerts: List<Alert>?) {

                _alertsResult?.value = ListAlertResult(success = alerts)
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