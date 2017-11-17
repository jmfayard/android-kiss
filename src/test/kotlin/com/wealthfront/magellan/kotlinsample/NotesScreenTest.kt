package com.wealthfront.magellan.kotlinsample

import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.verify
import com.wealthfront.magellan.kotlinsample.data.SectionItem
import io.kotlintest.specs.StringSpec

/** Tests for [NotesScreen] **/
class NotesScreenTest : StringSpec() { init {
    val notes = fetchNotes()
    val adapter = mockAdapter

    val screen = NotesScreen()
    screen.setupForTests(TestNotes(adapter), mockNavigator, mockActivity)

    "Display loading message then the notes" {
        verify(adapter, atLeastOnce()).updateData(SectionItem.LoadingList)
        verify(adapter, atLeastOnce()).updateData(notes)
    }

    "Can add a note" {
        screen.display?.onAddNote?.invoke()
        verify(screen.navigator).goTo(AddNoteScreen())
    }
}

}