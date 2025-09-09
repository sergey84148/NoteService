package model

data class Comment(
    val id: Long,
    val noteId: Long, // ID заметки, к которой относится комментарий
    var text: String,
    var deleted: Boolean = false // Флаг удаления
)