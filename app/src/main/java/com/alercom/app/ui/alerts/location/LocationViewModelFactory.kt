package com.alercom.app.ui.alerts.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.repositories.ReferenceRepository



class LocationViewModelFactory   : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            return LocationViewModel(
                referenceRepository = ReferenceRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}