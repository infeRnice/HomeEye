package project.Cameras.network

import kotlinx.serialization.Serializable
import project.Cameras.models.Camera

@Serializable
data class CamerasApiResponse(
    val success: Boolean,
    val data: CamerasData
)

@Serializable
data class CamerasData(
    val room: List<String>,
    val cameras: List<Camera>
)