package com.alercom.app.ui.alerts.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.repositories.AlertRepository

class ListAlertViewModelFactory :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListAlertViewModel::class.java)) {
            return ListAlertViewModel(
                eventReportRepository = AlertRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}