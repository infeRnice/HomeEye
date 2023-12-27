package project.Cameras.data.repository

import project.Cameras.data.CamerasRealmDatabase
import project.Cameras.models.Camera
import project.Cameras.models.Door
import project.Cameras.network.ApiService

class DataRepository(private val apiService: ApiService, private val realmDatabase: CamerasRealmDatabase) {

    suspend fun getCameras(): List<Camera> {
        // Попробуйте получить данные из базы данных
        val cachedCameras = realmDatabase.getCameras()
        if (cachedCameras.isNotEmpty()) {
            return cachedCameras
        }
        // Если данные в базе данных отсутствуют, делаем запрос к API
        val camerasFromApi = apiService.getCameras()

        //save data to db
        realmDatabase.saveCameras(camerasFromApi)

        return camerasFromApi
    }

    suspend fun getDoors(forceUpdate: Boolean = false): List<Door> {
        if (!forceUpdate) {
            val cachedDoors = realmDatabase.getDoors()
            if (cachedDoors.isNotEmpty()) {
                return cachedDoors
            }
        }

        val doorsFromApi = apiService.getDoors()
        realmDatabase.saveDoors(doorsFromApi)

        return doorsFromApi
    }
}