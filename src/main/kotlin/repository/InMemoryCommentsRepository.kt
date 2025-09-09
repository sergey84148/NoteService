
package com.example.notes.repository

import model.Comment
import repository.NotesRepository

class InMemoryCommentsRepository(private val notesRepo: NotesRepository) : CommentsRepository {
    private val comments = mutableListOf<Comment>()

    override fun createComment(comment: Comment) {
        val existingNote = notesRepo.getById(comment.noteId)
        if (existingNote == null || existingNote.deleted) {
            throw IllegalArgumentException("Нельзя создать комментарий к удалённой или несуществующей заметке.")
        }
        comments.add(comment)
    }

    override fun editComment(comment: Comment) {
        val existingComment = comments.find { it.id == comment.id }
        if (existingComment != null && !existingComment.deleted) {
            existingComment.text = comment.text
        } else {
            throw IllegalArgumentException("Редактировать можно только существующие и не удалённые комментарии.")
        }
    }

    override fun deleteComment(id: Long) {
        val commentToRemove = comments.find { it.id == id }
        commentToRemove?.deleted = true
    }

    override fun restoreComment(id: Long) {
        val commentToRestore = comments.find { it.id == id }
        commentToRestore?.deleted = false
    }

    override fun getComments(noteId: Long): List<Comment> {
        return comments.filter { it.noteId == noteId && !it.deleted }
    }
}