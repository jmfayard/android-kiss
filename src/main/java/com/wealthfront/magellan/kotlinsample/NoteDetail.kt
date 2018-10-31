package com.wealthfront.magellan.kotlinsample

import android.widget.FrameLayout
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.marcinmoskala.kotlinandroidviewbindings.bindToText

interface NoteDetail {
    var title: String
    var description: String
    var onEditNote: UiCallback
}

fun FrameLayout.displayNoteDetail() = object : NoteDetail {
    override var title: String by bindToText(R.id.note_detail_title)

    override var description: String by bindToText(R.id.note_detail_description)

    override var onEditNote: UiCallback by bindToClick(R.id.note_detail_edit)
}

data class TestNoteDetail(
        override var title: String,
        override var description: String,
        override var onEditNote: UiCallback = NOOP
) : NoteDetail {
    companion object {
        val EMPTY = TestNoteDetail("", "")
    }
}