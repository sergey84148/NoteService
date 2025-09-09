

import model.Note
import repository.NotesRepository

class InMemoryNotesRepository : NotesRepository {
    private val notes = mutableListOf<Note>()

    override fun add(note: Note) {
        notes.add(note)
    }

    override fun edit(note: Note) {
        if (!note.deleted) { // Нельзя редактировать удалённую заметку
            notes.find { it.id == note.id }?.let { updated ->
                updated.title = note.title
                updated.content = note.content
            }
        }
    }

    override fun delete(id: Long) {
        notes.find { it.id == id }?.deleted = true
    }

    override fun restore(id: Long) {
        notes.find { it.id == id }?.deleted = false
    }

    override fun get() = notes.filterNot { it.deleted }

    override fun getById(id: Long) = notes.firstOrNull { it.id == id && !it.deleted }
}