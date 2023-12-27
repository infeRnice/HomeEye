package project.Cameras

import android.app.Application
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import project.Cameras.data.CamerasRealmDatabase
import project.Cameras.data.models.RealmCamera
import project.Cameras.data.models.RealmDoor
import project.Cameras.network.ApiService
import project.Cameras.network.createApiService


class CamerasApplication : Application() {
    lateinit var realmDatabase: CamerasRealmDatabase
    lateinit var apiService: ApiService

    override fun onCreate() {
        super.onCreate()

        val config = RealmConfiguration.Builder(schema = setOf(RealmCamera::class, RealmDoor::class))
            .name("cameras.db")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()

        // Создание экземпляра ApiService
        apiService = createApiService() // Убедитесь, что функция createApiService() корректно создает экземпляр

        // Создание экземпляра CamerasRealmDatabase и передача apiService
        realmDatabase = CamerasRealmDatabase(Realm.open(config), apiService)


        /*// Используйте конфигурацию напрямую при создании экземпляров Realm
        val realm = Realm.open(config)
        // После использования, закройте Realm
        realm.close()*/
    }
}
