package com.wealthfront.magellan.kotlinsample

import com.nhaarman.mockito_kotlin.verify
import com.wealthfront.magellan.kotlinsample.data.Note
import io.kotlintest.specs.StringSpec

class NoteDetailScreenTest : StringSpec() {
    lateinit var notes: List<Note>

    init {

        App.repository.getNotes { notes = it }

        "Show first note" {
            val note = notes.first()
            val screen = NoteDetailScreen(note.id).mockWith()

            verify(screen.view).title = note.title
            verify(screen.view).description = note.description
        }

        "Invalid note: go back" {
            val screen = NoteDetailScreen("INVALID").mockWith()

            verify(screen.navigator).goBack()
        }

        "Going in edit note" {
            val note = notes.first()
            val screen = NoteDetailScreen(note.id).mockWith()

            screen.onEditNote()

            verify(screen.navigator).goTo(AddNoteScreen(note.id))
        }

    }
}