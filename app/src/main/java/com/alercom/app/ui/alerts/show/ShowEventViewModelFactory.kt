package com.alercom.app.ui.alerts.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.repositories.AlertRepository
import com.alercom.app.repositories.EventTypeRepository
import com.alercom.app.repositories.ReferenceRepository


class ShowEventViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowEventViewModel::class.java)) {
            return ShowEventViewModel(
                affectsRangeRepository = ReferenceRepository(),
                alertRepository = AlertRepository(),
                eventTypeRepository= EventTypeRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}