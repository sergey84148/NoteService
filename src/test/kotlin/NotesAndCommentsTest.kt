import model.Comment
import model.Note
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import repository.InMemoryCommentsRepository

internal class NotesAndCommentsTest {
    @Test
    fun testAddAndGetNote() {
        val repo = InMemoryNotesRepository()
        val note = Note(1L, "Title", "Content")
        repo.add(note)
        assertEquals(listOf(note), repo.get())
    }

    @Test
    fun testEditNote() {
        val repo = InMemoryNotesRepository()
        val note = Note(1L, "Old Title", "Old Content")
        repo.add(note)
        val editedNote = note.copy(title = "New Title", content = "New Content")
        repo.edit(editedNote)
        assertEquals(editedNote, repo.get().first())
    }

    @Test
    fun testDeleteNote() {
        val repo = InMemoryNotesRepository()
        val note = Note(1L, "Title", "Content")
        repo.add(note)
        repo.delete(note.id)
        assertTrue(repo.get().isEmpty()) // Проверяем, что удаленная заметка больше не видима
    }

    @Test
    fun testRestoreNote() {
        val repo = InMemoryNotesRepository()
        val note = Note(1L, "Title", "Content")
        repo.add(note)
        repo.delete(note.id)
        repo.restore(note.id)
        assertFalse(repo.getById(note.id)?.deleted ?: true)
    }

    @Test
    fun testCreateCommentOnNonExistingNote() {
        val notesRepo = InMemoryNotesRepository()
        val commentsRepo = InMemoryCommentsRepository(notesRepo)
        val nonExistentNoteId = 1L
        val comment = Comment(1L, nonExistentNoteId, "Some Text")

        // Исправляем ожидаемый тип исключения
        assertThrows(IllegalArgumentException::class.java) { commentsRepo.createComment(comment) }
    }

    @Test
    fun testGetCommentsForNote() {
        val notesRepo = InMemoryNotesRepository()
        val commentsRepo = InMemoryCommentsRepository(notesRepo)
        val note = Note(1L, "Title", "Content")
        notesRepo.add(note)
        val comment1 = Comment(1L, note.id, "First Comment")
        val comment2 = Comment(2L, note.id, "Second Comment")
        commentsRepo.createComment(comment1)
        commentsRepo.createComment(comment2)
        assertEquals(listOf(comment1, comment2), commentsRepo.getComments(note.id))
    }
}