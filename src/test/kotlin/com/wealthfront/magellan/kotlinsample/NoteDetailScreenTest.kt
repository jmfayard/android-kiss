package com.wealthfront.magellan.kotlinsample

import android.content.Context
import com.nhaarman.mockito_kotlin.verify
import com.wealthfront.magellan.kotlinsample.data.Note
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec

class NoteDetailScreenTest : StringSpec() {
    lateinit var notes: List<Note>

    init {

        App.repository.getNotes { notes = it }

        "Show first note" {
            val note = notes.first()
            val screen = NoteDetailScreen(note.id).withMockedView()

            verify(screen.view).title = note.title
            verify(screen.view).description = note.description
        }

        "Invalid note: go back" {
            val screen = NoteDetailScreen("INVALID").withMockedView()

            verify(screen.navigator).goBack()
        }

        "Going in edit note" {
            val note = notes.first()
            val screen = NoteDetailScreen(note.id).withMockedView()

            screen.onEditNote()

            verify(screen.navigator).goTo(AddNoteScreen(note.id))
        }

    }
}