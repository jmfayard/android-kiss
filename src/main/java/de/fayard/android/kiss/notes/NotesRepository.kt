package de.fayard.android.kiss.notes

import kotlinx.coroutines.coroutineScope

interface NotesRepository {

    suspend fun getNotes(): List<Note>

    suspend fun getNote(noteId: String): Note?

    suspend fun saveNote(note: Note)

    suspend fun refreshData()
}

object InMemoryRepository : NotesRepository {
    val api: NotesServiceApi = NotesServiceApiImpl

    var notes: List<Note>? = null

    override suspend fun getNotes(): List<Note> = coroutineScope {
        if (notes != null) {
            notes!!
        } else {
            api.getAllNotes().sortedByDescending { it.createdAt }
        }
    }

    override suspend fun getNote(noteId: String): Note? = coroutineScope {
        api.getNote(noteId)
    }

    override suspend fun saveNote(note: Note) = coroutineScope {
        api.saveNote(note)
        refreshData()
    }

    override suspend fun refreshData() {
        notes = null
    }
}
