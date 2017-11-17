package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.widget.FrameLayout
import com.wealthfront.magellan.kotlinsample.data.Note
import java.util.*


data class AddNoteScreen(val noteId: String? = null) : MagellanScreen<AddNote>() {

    override fun createView(context: Context) = MagellanView(context, AddNote.layout, FrameLayout::displayAddNote)

    val isEditMode = noteId != null

    override fun getTitle(context: Context?): String =
            if (isEditMode) "Edit Note" else "Add Note"

    override public fun onShow(context: Context?) {
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


