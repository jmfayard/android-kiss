package com.wealthfront.magellan.kotlinsample

import android.content.Context
import com.nhaarman.mockito_kotlin.verify
import com.wealthfront.magellan.kotlinsample.data.Note
import io.kotlintest.matchers.shouldBe
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec

class AddNoteScreenTest : StringSpec() {
    lateinit var notes: List<Note>
    val ctx : Context = mock()

    init {

        App.repository.getNotes { notes = it }

        "Add a new note" {
            val screen = AddNoteScreen(noteId = null)
                    .mockWith(title = "Note Title", description = "Note Description")

            screen.getTitle(ctx) shouldBe "Add Note"
            val note = screen.updatedNote()
            note.title shouldBe "Note Title"
            note.description shouldBe "Note Description"
        }

        "Edit an existing note" {
            val note = notes.first()
            val screen = AddNoteScreen(noteId = note.id).mockWith()

            screen.getTitle(ctx) shouldBe "Edit Note"
            verify(screen.view).title = note.title
            verify(screen.view).description = note.description
        }



    }
}