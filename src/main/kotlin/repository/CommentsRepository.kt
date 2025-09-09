

import javax.xml.stream.events.Comment

interface CommentsRepository {
    fun createComment(comment: Comment): Unit
    fun editComment(comment: Comment): Unit
    fun deleteComment(id: Long): Unit
    fun restoreComment(id: Long): Unit
    fun getComments(noteId: Long): List<model.Comment>
}