package com.wealthfront.magellan.kotlinsample.screens

import android.content.Context
import android.view.View
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.kotlinsample.R
import com.wealthfront.magellan.kotlinsample.inflateViewFrom

class NoteDetailView(context: Context) : BaseScreenView<NoteDetailScreen>(context) {
     init { inflateViewFrom(R.layout.notedetail_screen) }
    // val binding:  = .inflate(inflater, this, true)

}

class NoteDetailScreen : Screen<NoteDetailView>() {
    override fun createView(context: Context) = NoteDetailView(context)

    override fun getTitle(context: Context?): String = "NoteDetail"
}




