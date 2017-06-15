package com.wealthfront.magellan.kotlinsample

import android.content.Context
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.marcinmoskala.kotlinandroidviewbindings.bindToEditText
import com.marcinmoskala.kotlinandroidviewbindings.bindToText
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.kotlinsample.data.Note
import com.wealthfront.magellan.kotlinsample.databinding.AddnoteScreenBinding

class AddNoteView(context: Context) : BaseScreenView<AddNoteScreen>(context) {
    val binding: AddnoteScreenBinding = AddnoteScreenBinding.inflate(inflater, this, true)

    var onSubmit : () -> Unit by binding.addNoteSave.bindToClick()

    var title: String by binding.addNoteTitle.bindToEditText()

    var description: String by binding.addNoteDescription.bindToEditText()

    fun focus() = binding.addNoteTitle.requestFocus()
}

class AddNoteScreen(val noteId: String? = null) : Screen<AddNoteView>() {
    override fun createView(context: Context) = AddNoteView(context)

    val isEditMode = noteId != null

    override fun getTitle(context: Context?): String =
            if (isEditMode) "Edit Note" else "Add Note"

    override fun onShow(context: Context?) {
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

    fun updatedNote() : Note = if (isEditMode) {
        Note(id = noteId!!, title = view.title, description = view.description)
    } else {
        Note(title = view.title, description = view.description)
    }
}


