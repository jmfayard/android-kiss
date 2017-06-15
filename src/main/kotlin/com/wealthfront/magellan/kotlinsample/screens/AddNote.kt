package com.wealthfront.magellan.kotlinsample.screens

import android.content.Context
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.kotlinsample.R
import com.wealthfront.magellan.kotlinsample.inflateViewFrom

class AddNoteView(context: Context) : BaseScreenView<AddNoteScreen>(context) {
     init { inflateViewFrom(R.layout.addnote_screen) }
    // val binding:  = .inflate(inflater, this, true)

}

class AddNoteScreen : Screen<AddNoteView>() {
    override fun createView(context: Context) = AddNoteView(context)

    override fun getTitle(context: Context?): String = "AddNote"
}


