package com.alercom.app.ui.alerts.create.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.data.repositories.EventTypeRepository
import com.alercom.app.data.repositories.ReferenceRepository


class EventCategoryViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventCategoryViewModel::class.java)) {
            return EventCategoryViewModel(
                referenceRepository = ReferenceRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}