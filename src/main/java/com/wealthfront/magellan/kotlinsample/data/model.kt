package com.wealthfront.magellan.kotlinsample.data

import java.util.*

interface ListItem

data class Note(
        val id: String = UUID.randomUUID().toString(),
        val description: String,
        val title: String,
        val url: String? = null
): ListItem

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

    fun getNotes(onNotesLoaded:  (List<Note>) -> Unit)

    fun getNote(noteId: String, onNoteLoaded: (Note?) -> Unit)

    fun saveNote(note: Note)

    fun refreshData()

}



data class SectionItem(val title: String): ListItem

val LoadingList = listOf(SectionItem("Loading, please wait"))
