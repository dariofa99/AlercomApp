package com.alercom.app.ui.alerts.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.repositories.EventTypeRepository


class EventViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(
                eventTypeRepository = EventTypeRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}