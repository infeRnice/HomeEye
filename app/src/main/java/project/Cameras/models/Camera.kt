package project.Cameras.models

import kotlinx.serialization.Serializable

@Serializable
data class Camera(
    val id: Int,
    val name: String,
    val snapshot: String, // Обновленное имя поля
    val room: String?, // Дополнительное поле, может быть null
    val favorites: Boolean = false, // Дополнительное поле
    val rec: Boolean = false// Дополнительное поле
)
