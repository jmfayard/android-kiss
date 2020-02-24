package com.wealthfront.magellan.kotlinsample

import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.marcinmoskala.kotlinandroidviewbindings.bindToEditText
import com.marcinmoskala.kotlinandroidviewbindings.bindToRequestFocus
import com.wealthfront.magellan.ScreenSetups
import com.wealthfront.magellan.UiCallback


fun ScreenSetups.displayAddNote() = object : AddNote {

  override var onSubmit: UiCallback by bindToClick(com.wealthfront.magellan.kotlinsample.R.id.add_note_save)

  override var title: String by bindToEditText(com.wealthfront.magellan.kotlinsample.R.id.add_note_title)

  override var description: String by bindToEditText(com.wealthfront.magellan.kotlinsample.R.id.add_note_description)

  override val focus: UiCallback by bindToRequestFocus(com.wealthfront.magellan.kotlinsample.R.id.add_note_title)
}
