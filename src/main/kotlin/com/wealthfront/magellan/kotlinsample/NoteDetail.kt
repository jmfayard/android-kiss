package com.wealthfront.magellan.kotlinsample

import android.content.Context
import com.marcinmoskala.kotlinandroidviewbindings.bindToText
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.kotlinsample.databinding.NotedetailScreenBinding

class NoteDetailView(context: Context) : BaseScreenView<NoteDetailScreen>(context) {

    val binding: NotedetailScreenBinding = NotedetailScreenBinding.inflate(inflater, this, true)

    var title: String by binding.noteDetailTitle.bindToText()
    var description: String by binding.noteDetailDescription.bindToText()

}

class NoteDetailScreen(val noteId: String) : Screen<NoteDetailView>() {
    override fun createView(context: Context) = NoteDetailView(context)

    override fun getTitle(context: Context?): String = "Note Detail"

    override fun onShow(context: Context?) {
        App.repository.getNote(noteId) { note ->
            if (note == null) {
                toast("ERROR, cannot find note $noteId")
            } else {
                view?.title = note.title
                view?.description = note.description
            }
        }
    }
}




