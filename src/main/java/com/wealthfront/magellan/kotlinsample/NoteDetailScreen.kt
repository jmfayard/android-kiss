package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.widget.FrameLayout


class NoteDetailScreen(val noteId: String) : MagellanScreen<NoteDetail>(
        R.layout.notedetail_screen, R.string.title_notedetails, FrameLayout::displayNoteDetail
) {

    override fun onShow(context: Context?) {
        display?.onEditNote = this::onEditNote

        App.repository.getNote(noteId) { note ->
            if (note == null) {
                toast("ERROR, cannot find note $noteId")
                navigator.goBack()
            } else {
                display?.title = note.title
                display?.description = note.description
            }
        }
    }

    fun onEditNote() = navigator.goTo(AddNoteScreen(noteId))
}




