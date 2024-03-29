package com.alercom.app.ui.institutional_routes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.data.repositories.InstitutionalRoutesRepository

class InstitutionalRouteViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InstitutionalRouteViewModel::class.java)) {
            return InstitutionalRouteViewModel(
                institutionalRoutesRepository = InstitutionalRoutesRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}