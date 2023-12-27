package project.Cameras.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.Cameras.data.CamerasRealmDatabase
import project.Cameras.data.repository.DataRepository

class CamerasViewModelFactory(private val repository: DataRepository, val realmDatabase: CamerasRealmDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CamerasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CamerasViewModel(repository, realmDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}