package com.alercom.app.ui.use

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alercom.app.data.repositories.InstitutionRepository
import com.alercom.app.data.repositories.ReferenceRepository

class AlercomUseViewModelFactory :  ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlercomUseViewModel::class.java)) {
            return AlercomUseViewModel(
                institutionRepository= InstitutionRepository(),
                referenceRepository = ReferenceRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}