package project.Cameras.network

import kotlinx.serialization.Serializable
import project.Cameras.models.Door

@Serializable
data class DoorsApiResponse(
    val success: Boolean,
    val data: List<Door>
)

