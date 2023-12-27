package project.Cameras.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmDoor : RealmObject {
    @PrimaryKey
    var id: Int = 0 // Уникальный идентификатор
    var name: String = "" // Название двери
    var snapshot: String? = null //image
    var room: String? = null
    var favorites: Boolean = false
}