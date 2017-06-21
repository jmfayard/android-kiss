package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.marcinmoskala.kotlinandroidviewbindings.bindToText
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen

class NoteDetailView(context: Context) : BaseScreenView<NoteDetailScreen>(context) {

    init {
        inflate(context, R.layout.notedetail_screen, this)
    }

    var title: String by bindToText(R.id.note_detail_title)

    var description: String by bindToText(R.id.note_detail_description)

    var onEditNote: () -> Unit by bindToClick(R.id.note_detail_edit)

}

class NoteDetailScreen(val noteId: String) : Screen<NoteDetailView>() {
    override fun createView(context: Context) = NoteDetailView(context)

    override fun getTitle(context: Context?): String = "Note Detail"

    @VisibleForTesting
    override public fun onShow(context: Context?) {
        view.onEditNote = this::onEditNote

        App.repository.getNote(noteId) { note ->
            if (note == null) {
                toast("ERROR, cannot find note $noteId")
                navigator.goBack()
            } else {
                view?.title = note.title
                view?.description = note.description
            }
        }
    }

    fun onEditNote() = navigator.goTo(AddNoteScreen(noteId))
}




