package repository

import model.Note

interface NotesRepository {
    fun add(note: Note): Unit
    fun edit(note: Note): Unit
    fun delete(id: Long): Unit
    fun restore(id: Long): Unit
    fun get(): List<Note>
    fun getById(id: Long): Note?
}