package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.widget.FrameLayout
import com.wealthfront.magellan.kotlinsample.data.Note
import java.util.*


data class AddNoteScreen(val noteId: String? = null) : MagellanScreen<AddNote>(
        screenLayout = R.layout.addnote_screen,
        screenTitle = if (noteId != null) R.string.title_editnote else R.string.title_addnote,
        screenSetup = FrameLayout::displayAddNote
) {

    val isEditMode = noteId != null

    override fun onShow(context: Context?) {
        if (isEditMode) {
            showExistingNote()
        }
        display?.focus()

        display?.onSubmit = {
            view.hideKeyboard()
            App.repository.saveNote(updatedNote())
            navigator.goBack()
        }
    }

    fun showExistingNote() {
        App.repository.getNote(noteId!!) { note ->
            if (note == null) {
                navigator?.goBack()
            } else {
                display?.title = note.title
                display?.description = note.description
            }
        }
    }

    fun updatedNote(): Note {
        val d = requireNotNull(display)
        val title = d.title
        val description = d.description
        val id = if (isEditMode) noteId!! else UUID.randomUUID().toString()
        return Note(id, description, title)
    }
}


