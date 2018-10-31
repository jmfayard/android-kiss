package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.widget.FrameLayout
import kotlinx.coroutines.launch


class NoteDetailScreen(
        val noteId: String,
        val repository: NotesRepository = InMemoryRepository
) : MagellanScreen<NoteDetail>(
        R.layout.notedetail_screen, R.string.title_notedetails, FrameLayout::displayNoteDetail
) {

    override fun onShow(context: Context) {
        super.onShow(context)
        display?.onEditNote = this::onEditNote
        fetchAndDisplayNote()
    }

    fun fetchAndDisplayNote() = launch {
        val note = repository.getNote(noteId)
        if (note == null) {
            toast("ERROR, cannot find note $noteId")
            navigator.goBack()
        } else {
            display?.title = note.title
            display?.description = note.description
        }
    }

    fun onEditNote() = navigator.goTo(AddNoteScreen(noteId))
}




