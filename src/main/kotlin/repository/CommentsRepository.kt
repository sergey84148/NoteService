
package com.example.notes.repository

import model.Comment

interface CommentsRepository {
    fun createComment(comment: Comment): Unit
    fun editComment(comment: Comment): Unit
    fun deleteComment(id: Long): Unit
    fun restoreComment(id: Long): Unit
    fun getComments(noteId: Long): List<Comment>
}