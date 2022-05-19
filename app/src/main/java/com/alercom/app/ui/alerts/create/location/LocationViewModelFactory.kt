package com.alercom.app.ui.alerts.create.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.data.repositories.ReferenceRepository



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