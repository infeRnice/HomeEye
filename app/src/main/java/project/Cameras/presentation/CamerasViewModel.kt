package project.Cameras.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import project.Cameras.data.CamerasRealmDatabase
import project.Cameras.data.repository.DataRepository
import project.Cameras.models.Camera
import project.Cameras.models.Door

class CamerasViewModel(private val repository: DataRepository, val realmDatabase: CamerasRealmDatabase) : ViewModel() {

    private val _cameras = MutableLiveData<List<Camera>>()
    val cameras: LiveData<List<Camera>> = _cameras

    private val _doors = MutableLiveData<List<Door>>()
    val doors: LiveData<List<Door>> = _doors

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadCameras()
        loadDoors()
    }

    private fun loadCameras() {
        viewModelScope.launch {
            try {
                println("Loading cameras from repository")
                val camerasData = repository.getCameras()
                println("Loaded cameras: $camerasData")
                _cameras.value = camerasData

            } catch (e: Exception) {
                println("Error: cameras didnt load: ${e.message}")
            }
        }
    }

    fun loadDoors() {
        viewModelScope.launch {
            try {
                _doors.value = repository.getDoors()
                _isLoading.value = true
                delay(1000L)
                _isLoading.value = false
            } catch (e: Exception) {
                println("Error: doors didnt load: ${e.message}")
            }
        }
    }

    fun refreshDoors() {
        viewModelScope.launch {
            _isLoading.value = true
            _doors.value = repository.getDoors(forceUpdate = true)
            _isLoading.value = false
        }
    }

    fun updateDoorInList(updatedDoor: Door) {
        // Получаем текущий список дверей
        val currentDoors = _doors.value ?: return

        // Обновляем дверь в списке
        val updatedDoors = currentDoors.map { if (it.id == updatedDoor.id) updatedDoor else it }

        // Устанавливаем обновленный список
        _doors.value = updatedDoors
    }

    fun updateCameraInList(updatedCamera: Camera) {
        // Получаем текущий список дверей
        val currentCameras = _cameras.value ?: return

        // Обновляем дверь в списке
        val updatedCameras = currentCameras.map { if (it.id == updatedCamera.id) updatedCamera else it }

        // Устанавливаем обновленный список
        _cameras.value = updatedCameras
    }

}