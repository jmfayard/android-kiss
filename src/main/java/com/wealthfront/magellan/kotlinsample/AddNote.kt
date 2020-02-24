package com.wealthfront.magellan.kotlinsample

import com.wealthfront.magellan.IDisplay
import com.wealthfront.magellan.UiCallback

interface AddNote : IDisplay {
  var onSubmit: UiCallback
  var title: String
  var description: String
  val focus: UiCallback
  fun focus() = focus.invoke()
}