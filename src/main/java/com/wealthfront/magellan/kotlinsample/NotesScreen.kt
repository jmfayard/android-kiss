package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.widget.FrameLayout
import com.wealthfront.magellan.kotlinsample.data.LoadingList
import com.wealthfront.magellan.kotlinsample.data.Note


class NotesScreen : MagellanScreen<Notes>(
        R.layout.notes_screen, R.string.title_notes, FrameLayout::displayNotes
) {

    public override fun onShow(context: Context?) {
        display?.onAddNote = { addNote() }

        display?.setupRecyclerView { item ->
            onItemClicked(item)
        }
        display?.updateRecyclerViewData(LoadingList)

        App.Companion.repository.getNotes { notes: List<Note> ->
            display?.updateRecyclerViewData(notes)
        }
    }

    fun addNote() = navigator.goTo(AddNoteScreen())

    fun onItemClicked(item: Note) {
        navigator.goTo(NoteDetailScreen(item.id))
    }

}

