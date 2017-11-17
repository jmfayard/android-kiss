package com.wealthfront.magellan.kotlinsample

import com.nhaarman.mockito_kotlin.verify
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class NoteDetailScreenTest : StringSpec() { init {
    val notes = fetchNotes()
    val idFirstNote = notes.first().id

    "Show first note" {
        val screen = NoteDetailScreen(idFirstNote)
        screen.setupForTests(TestNoteDetail.EMPTY, mockNavigator, mockActivity)

        val display = requireNotNull(screen.display)
        val note = notes.first()
        display.title shouldBe note.title
        display.description shouldBe note.description
    }

    "Invalid note: go back" {
        val screen = NoteDetailScreen("INVALID")
        screen.setupForTests(TestNoteDetail.EMPTY, mockNavigator, mockActivity)

        verify(screen.navigator).goBack()
    }

    "Going in edit note" {
        val screen = NoteDetailScreen(idFirstNote)
        screen.setupForTests(TestNoteDetail.EMPTY, mockNavigator, mockActivity)

        screen.onEditNote()

        verify(screen.navigator).goTo(AddNoteScreen(idFirstNote))
    }

}
}