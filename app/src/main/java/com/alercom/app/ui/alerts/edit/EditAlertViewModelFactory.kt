package com.alercom.app.ui.alerts.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.data.repositories.AlertRepository
import com.alercom.app.data.repositories.EventTypeRepository
import com.alercom.app.data.repositories.ReferenceRepository

class EditAlertViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditAlertViewModel::class.java)) {
            return EditAlertViewModel(
                affectsRangeRepository = ReferenceRepository(),
                alertRepository = AlertRepository(),
                eventTypeRepository= EventTypeRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}