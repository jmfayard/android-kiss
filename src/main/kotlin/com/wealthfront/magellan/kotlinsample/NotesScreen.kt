package com.wealthfront.magellan.kotlinsample

import android.content.Context
import com.wealthfront.magellan.kotlinsample.data.Note
import com.wealthfront.magellan.kotlinsample.data.SectionItem


class NotesScreen : MagellanScreen<Notes>() {

    override fun createView(context: Context): MagellanView<Notes> =
            MagellanView(context, Notes.layout, MagellanView<Notes>::displayNotes)

    override fun getTitle(context: Context?) = "Notes"

    public override fun onShow(context: Context?) {
        display?.onAddNote = { addNote() }

        display?.slimAdapter?.updateData(SectionItem.LoadingList)

        App.Companion.repository.getNotes { notes: List<Note> ->
            display?.slimAdapter?.updateData(notes)
        }
    }

    fun addNote() = navigator.goTo(AddNoteScreen())

    fun onItemClicked(item: Note) {
        navigator.goTo(NoteDetailScreen(item.id))
    }

}

