package com.wealthfront.magellan.kotlinsample

import android.support.annotation.LayoutRes
import android.widget.FrameLayout
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.marcinmoskala.kotlinandroidviewbindings.bindToEditText
import com.marcinmoskala.kotlinandroidviewbindings.bindToRequestFocus


interface AddNote : IDisplay {
    var onSubmit: UiCallback
    var title: String
    var description: String
    val focus: UiCallback
    fun focus() = focus.invoke()

    companion object {
        @LayoutRes
        val layout = R.layout.addnote_screen
        val TEST = TestAddNote("", "")
    }
}

fun FrameLayout.displayAddNote() = object : AddNote {

    override var onSubmit: UiCallback by bindToClick(R.id.add_note_save)

    override var title: String by bindToEditText(R.id.add_note_title)

    override var description: String by bindToEditText(R.id.add_note_description)

    override val focus: UiCallback by bindToRequestFocus(R.id.add_note_title)

}

data class TestAddNote(
        override var title: String,
        override var description: String,
        override var onSubmit: UiCallback = NOOP,
        override val focus: UiCallback = NOOP
) : AddNote