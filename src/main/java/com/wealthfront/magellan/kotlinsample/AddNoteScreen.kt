package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.widget.FrameLayout
import kotlinx.coroutines.launch
import java.util.*


data class AddNoteScreen(
        val noteId: String? = null,
        val repository: NotesRepository = InMemoryRepository
) : MagellanScreen<AddNote>(
        screenLayout = R.layout.addnote_screen,
        screenTitle = if (noteId != null) R.string.title_editnote else R.string.title_addnote,
        screenSetup = FrameLayout::displayAddNote
) {

    val isEditMode = noteId != null

    override fun onShow(context: Context) {
        super.onShow(context)
        if (isEditMode) { showExistingNote() }
        display?.focus()
        display?.onSubmit = this::onSubmit
    }

    fun onSubmit() {
        launch {
            view.hideKeyboard()
            repository.saveNote(updatedNote())
            navigator.goBack()
        }
    }

    fun showExistingNote() {
        launch {
            val note = repository.getNote(noteId!!)
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


