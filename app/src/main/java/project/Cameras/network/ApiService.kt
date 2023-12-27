package project.Cameras.network

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import project.Cameras.models.Camera
import project.Cameras.models.Door


fun createApiService(): ApiService {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    return ApiService(client)
}


class ApiService(private val client: HttpClient) {

    suspend fun getCameras(): List<Camera> {
        return try {
            val response: CamerasApiResponse = client.get("http://cars.cprogroup.ru/api/rubetek/cameras/").body()
            response.data.cameras
        } catch (e: Exception) {
            println("Error fetching cameras: ${e.message}")
            emptyList()
        }

    }

    suspend fun getDoors(): List<Door> {
        return try {
            val response: DoorsApiResponse = client.get("http://cars.cprogroup.ru/api/rubetek/doors/").body()
            println("API Response: $response")
            response.data
        } catch (e: Exception) {
            println("Error fetching doors: ${e.message}")
            emptyList()
        }
    }

}

