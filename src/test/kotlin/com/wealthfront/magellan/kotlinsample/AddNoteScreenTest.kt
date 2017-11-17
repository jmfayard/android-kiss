package com.wealthfront.magellan.kotlinsample

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.StringSpec


class AddNoteScreenTest : StringSpec() {init {
    val notes = fetchNotes()

    "Add a new note" {
        val screen = AddNoteScreen(noteId = null)
        screen.setupForTests(AddNote.TEST, mockNavigator, mockActivity)

        screen.getTitle(mockContext) shouldBe "Add Note"

        val display = requireNotNull(screen.display)
        display.title shouldBe ""
        display.description shouldBe ""

    }

    "Edit an existing note" {
        val note = notes.first()
        val screen = AddNoteScreen(noteId = note.id)
        screen.setupForTests(AddNote.TEST, mockNavigator, mockActivity)

        screen.getTitle(mockContext) shouldBe "Edit Note"

        val display = requireNotNull(screen.display)
        display.title shouldBe note.title
        display.description shouldBe note.description

    }


    "Submitting the note" {
        val screen = AddNoteScreen(noteId = null)
        screen.setupForTests(AddNote.TEST, mockNavigator, mockActivity)

        val display = requireNotNull(screen.display)
        display.title = "Note Title"
        display.description = "Note Description"

        screen.updatedNote().let { (id, description, title, _) ->
            id shouldNotBe ""
            title shouldBe display.title
            description shouldBe display.description
        }
    }


}
}