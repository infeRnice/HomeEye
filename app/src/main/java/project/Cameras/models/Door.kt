package project.Cameras.models

import kotlinx.serialization.Serializable

@Serializable
data class Door(
    val id: Int,
    val name: String,
    val snapshot: String? = null, // Добавлено поле для изображения
    val room: String?,
    val favorites: Boolean
)
