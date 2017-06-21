package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.marcinmoskala.kotlinandroidviewbindings.bindToEditText
import com.marcinmoskala.kotlinandroidviewbindings.bindToRequestFocus
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.kotlinsample.data.Note

class AddNoteView(context: Context) : BaseScreenView<AddNoteScreen>(context) {
    init {
        inflate(context, R.layout.addnote_screen, this)
    }

    var onSubmit: () -> Unit by bindToClick(R.id.add_note_save)

    var title: String by bindToEditText(R.id.add_note_title)

    var description: String by bindToEditText(R.id.add_note_description)

    val focus: () -> Unit by bindToRequestFocus(R.id.add_note_title)
}

data class AddNoteScreen(val noteId: String? = null) : Screen<AddNoteView>() {
    override fun createView(context: Context) = AddNoteView(context)

    val isEditMode = noteId != null

    override fun getTitle(context: Context?): String =
            if (isEditMode) "Edit Note" else "Add Note"

    @VisibleForTesting
    override public fun onShow(context: Context?) {
        if (isEditMode) {
            showExistingNote()
        }
        view.focus()

        view.onSubmit = {
            view.hideKeyboard()
            App.repository.saveNote(updatedNote())
            navigator.goBack()
        }
    }

    fun showExistingNote() {
        App.repository.getNote(noteId!!) { note ->
            if (note == null) return@getNote
            view.title = note.title
            view.description = note.description
        }
    }

    fun updatedNote(): Note = if (isEditMode) {
        Note(id = noteId!!, title = view.title, description = view.description)
    } else {
        Note(title = view.title, description = view.description)
    }
}


