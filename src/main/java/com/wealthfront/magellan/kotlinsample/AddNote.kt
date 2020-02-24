package com.wealthfront.magellan.kotlinsample

import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.marcinmoskala.kotlinandroidviewbindings.bindToEditText
import com.marcinmoskala.kotlinandroidviewbindings.bindToRequestFocus
import com.wealthfront.magellan.IDisplay
import com.wealthfront.magellan.ScreenSetups
import com.wealthfront.magellan.UiCallback

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