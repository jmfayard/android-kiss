package de.fayard.android.kiss.notes

import com.marcinmoskala.kotlinandroidviewbindings.*
import de.fayard.android.kiss.*
import de.fayard.android.kiss.R

interface AddNote : IDisplay {
  var onSubmit: UiCallback
  var title: String
  var description: String
  val focus: UiCallback
  fun focus() = focus.invoke()
}

fun ScreenSetups.displayAddNote() = object : AddNote {

  override var onSubmit: UiCallback by bindToClick(R.id.add_note_save)

  override var title: String by bindToEditText(R.id.add_note_title)

  override var description: String by bindToEditText(R.id.add_note_description)

  override val focus: UiCallback by bindToRequestFocus(R.id.add_note_title)
}