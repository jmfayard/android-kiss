package com.wealthfront.magellan.kotlinsample

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

/**
 * Defines an interface to the service API that is used by this application. All data request should
 * be piped through this interface.
 */
interface NotesServiceApi {

    suspend fun getAllNotes(): List<Note>

    suspend fun getNote(noteId: String): Note?

    suspend fun saveNote(note: Note)
}

/**
 * Implementation of the Notes Service API that adds a latency simulating network.
 */
object NotesServiceApiImpl : NotesServiceApi {
    val NOTES = mutableListOf(
        Note(
            description = "The simplest navigation library for Android.",
            title = "Magellan",
            url = "https://github.com/wealthfront/magellan"
        ),
        Note(
            description = "Statically typed programming language for modern multiplatform applications. 100% interoperable with Java™ and Android™.",
            title = "Kotlin",
            url = "http://kotlinlang.org/"
        ),
        Note(description = "Coroutines are awesome", title = "Kotlin Coroutines")
    )
    val NOTES_SERVICE_DATA: MutableMap<String, Note> = NOTES.associateBy { it.id }.toMutableMap()

    override suspend fun getAllNotes(): List<Note> = coroutineScope {
        delay(700)
        NOTES_SERVICE_DATA.values.toList()
    }

    override suspend fun getNote(noteId: String): Note? = coroutineScope {
        delay(50)
        NOTES_SERVICE_DATA[noteId]
    }

    override suspend fun saveNote(note: Note) {
        delay(50)
        NOTES_SERVICE_DATA[note.id] = note
    }
}