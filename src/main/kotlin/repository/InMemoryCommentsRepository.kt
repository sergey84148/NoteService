package repository


import CommentsRepository
import model.Comment

/**
 * Реализация репозитория комментариев, основанная на in-memory хранении.
 */
class InMemoryCommentsRepository(private val notesRepo: NotesRepository) : CommentsRepository {
    /**
     * Хранение комментариев в оперативной памяти.
     */
    private val comments = mutableListOf<Comment>()

    /**
     * Создать новый комментарий к существующей заметке.
     *
     * @param comment Объект нового комментария.
     */
    fun createComment(comment: Comment) {
        // Проверяем существование заметки перед добавлением комментария
        val existingNote = notesRepo.getById(comment.noteId)
        if (existingNote == null || existingNote.deleted) {
            throw IllegalArgumentException("Нельзя создать комментарий к удалённой или несуществующей заметке.")
        }
        comments.add(comment)
    }

    /**
     * Редактировать существующий комментарий.
     *
     * @param comment Новый объект комментария с изменёнными данными.
     */
    fun editComment(comment: Comment) {
        // Найти существующий комментарий по ID и обновить его, если он не удалён
        val existingComment = comments.find { it.id == comment.id }
        if (existingComment != null && !existingComment.deleted) {
            existingComment.text = comment.text
        } else {
            throw IllegalArgumentException("Редактировать можно только существующие и не удалённые комментарии.")
        }
    }

    override fun createComment(comment: javax.xml.stream.events.Comment) {
        TODO("Not yet implemented")
    }

    override fun editComment(comment: javax.xml.stream.events.Comment) {
        TODO("Not yet implemented")
    }

    /**
     * Логически удалить комментарий, установив флаг deleted.
     *
     * @param id Идентификатор комментария.
     */
    override fun deleteComment(id: Long) {
        val commentToRemove = comments.find { it.id == id }
        commentToRemove?.deleted = true
    }

    /**
     * Восстановить ранее удалённый комментарий.
     *
     * @param id Идентификатор комментария.
     */
    override fun restoreComment(id: Long) {
        val commentToRestore = comments.find { it.id == id }
        commentToRestore?.deleted = false
    }

    /**
     * Получить все доступные комментарии для заданной заметки.
     *
     * @param noteId Идентификатор заметки.
     * @return Список активных комментариев для данной заметки.
     */
    override fun getComments(noteId: Long): List<Comment> {
        return comments.filter { it.noteId == noteId && !it.deleted }
    }
}