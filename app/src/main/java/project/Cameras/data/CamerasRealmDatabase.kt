package project.Cameras.data

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import project.Cameras.data.models.RealmCamera
import project.Cameras.data.models.RealmDoor
import project.Cameras.models.Camera
import project.Cameras.models.Door
import project.Cameras.network.ApiService

class CamerasRealmDatabase(private val realm: Realm, private val apiService: ApiService) {

    // Сохранение списка камер
    suspend fun saveCameras(cameras: List<Camera>) {
        realm.write {
            cameras.forEach { camera ->
                println("Saving camera to DB: ID=${camera.id}, Name=${camera.name}, Snapshot=${camera.snapshot}, Room=${camera.room}, Favorites=${camera.favorites}, Rec=${camera.rec}")
                copyToRealm(RealmCamera().apply {
                    id = camera.id
                    name = camera.name
                    snapshot = camera.snapshot
                    room = camera.room
                    favorites = camera.favorites
                    rec = camera.rec
                })
            }
        }
    }

    suspend fun saveDoors(doors: List<Door>) {
        realm.write {
            doors.forEach { door ->
                copyToRealm(RealmDoor().apply {
                    id = door.id
                    name = door.name
                    snapshot = door.snapshot
                    room = door.room
                    favorites = door.favorites
                }, UpdatePolicy.ALL)
            }
        }
    }

    suspend fun updateCameraName(cameraId: Int, newName: String) {
        realm.write {
            val camera = query<RealmCamera>("id = $cameraId").first().find()
            camera?.name = newName
        }
    }

    suspend fun updateDoorName(doorId: Int, newName: String) {
        realm.write {
            val door = query<RealmDoor>("id = $doorId").first().find()
            door?.name = newName
        }
    }

    // Получение списка камер
    suspend fun getCameras(forceUpdate: Boolean = false): List<Camera> {
        val localCameras = realm.query<RealmCamera>().find()
        if (localCameras.isEmpty() || forceUpdate) {
            // Запрос данных с сервера и сохранение в БД
            val camerasFromServer = apiService.getCameras()
            saveCameras(camerasFromServer)
            return camerasFromServer
        } else {
            return localCameras.map { Camera(it.id, it.name, it.snapshot, it.room, it.favorites, it.rec) }
        }
    }

    // Получение списка камер
    suspend fun getDoors(/*forceUpdate: Boolean = false*/): List<Door> {
        val localDoors = realm.query<RealmDoor>().find()
        println("Local doors count: ${localDoors.size}")
        return localDoors.map { Door(it.id, it.name, it.snapshot, it.room, it.favorites) }
       /* if (localDoors.isEmpty() || forceUpdate) {
            // Запрос данных с сервера и сохранение в БД
            println("Fetching doors from API")
            val doorsFromServer = apiService.getDoors()
            saveDoors(doorsFromServer)
            return doorsFromServer
        } else {
            println("Returning doors from local database")
            return localDoors.map { Door(it.id, it.name, it.snapshot, it.room, it.favorites) }
        }*/
    }
}
