package project.Cameras.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmCamera : RealmObject {
    @PrimaryKey
    var id: Int = 0 // Уникальный идентификатор
    var name: String = "" // Название камеры
    var snapshot: String = "" // Обновленное имя поля
    var room: String? = null // Дополнительное поле, может быть null
    var favorites: Boolean = false// Дополнительное поле
    var rec: Boolean = false// Дополнительное поле
}
