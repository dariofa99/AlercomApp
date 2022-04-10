package com.alercom.app.ui.alerts.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.repositories.AlertRepository
import com.alercom.app.repositories.ReferenceRepository

class EditAlertViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditAlertViewModel::class.java)) {
            return EditAlertViewModel(
                affectsRangeRepository = ReferenceRepository(),
                alertRepository = AlertRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}