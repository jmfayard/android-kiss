package com.wealthfront.magellan.kotlinsample.data

import java.util.*


data class Note(
        val id : String = UUID.randomUUID().toString(),
        val title: String,
        val description: String,
        val url: String? = null
)

/**
 * Defines an interface to the service API that is used by this application. All data request should
 * be piped through this interface.
 */
interface NotesServiceApi {

    fun getAllNotes(onLoaded : (List<Note>) -> Unit)

    fun getNote(noteId: String, onLoaded: (Note?) -> Unit)

    fun saveNote(note: Note)
}

interface NotesRepository {

    fun getNotes(onNotesLoaded: (List<Note>) -> Unit)

    fun getNote(noteId: String, onNoteLoaded: (Note?) -> Unit)

    fun saveNote(note: Note)

    fun refreshData()

}



data class SectionItem(val title: String)
