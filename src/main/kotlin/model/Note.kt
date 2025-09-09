package model

data class Note(
    val id: Long,
    var title: String,
    var content: String,
    var deleted: Boolean = false // Флаг удаления
)