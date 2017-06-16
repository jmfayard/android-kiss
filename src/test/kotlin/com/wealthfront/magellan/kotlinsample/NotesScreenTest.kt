package com.wealthfront.magellan.kotlinsample

import android.content.Context
import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.verify
import com.wealthfront.magellan.kotlinsample.data.Note
import com.wealthfront.magellan.kotlinsample.data.SectionItem
import io.kotlintest.mock.mock
import io.kotlintest.specs.StringSpec
import net.idik.lib.slimadapter.SlimAdapter
import org.junit.Assert.*

class NotesScreenTest : StringSpec() {
    lateinit var notes: List<Note>
    val ctx : Context = mock()

    init {
        val adapter = mock<SlimAdapter>()
        val screen = NotesScreen().mockWith(adapter)

        App.repository.getNotes { notes = it }

        "First display a loading message" {
            verify(adapter).updateData(argThat { size == 1 && first() is SectionItem })
        }

        "Then the notes" {
            verify(adapter).updateData(notes)
        }

        "Can add a note" {
            screen.addNote()
            verify(screen.navigator).goTo(AddNoteScreen())
        }
    }

}