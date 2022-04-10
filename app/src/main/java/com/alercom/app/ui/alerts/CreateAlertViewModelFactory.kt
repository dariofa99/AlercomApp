package com.alercom.app.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.repositories.AlertRepository
import com.alercom.app.repositories.ReferenceRepository


class CreateAlertViewModelFactory :  ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateAlertViewModel::class.java)) {
            return CreateAlertViewModel(
                affectsRangeRepository = ReferenceRepository(),
                eventReportRepository = AlertRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/*class VMFactory(private val repository: UseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UseCase::class.java).newInstance(repository)
    }
}*/