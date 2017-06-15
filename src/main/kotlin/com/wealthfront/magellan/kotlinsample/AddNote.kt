package com.wealthfront.magellan.kotlinsample

class AddNoteView(context: android.content.Context) : com.wealthfront.magellan.BaseScreenView<AddNoteScreen>(context) {
     init { inflateViewFrom(com.wealthfront.magellan.kotlinsample.R.layout.addnote_screen) }
    // val binding:  = .inflate(inflater, this, true)

}

class AddNoteScreen : com.wealthfront.magellan.Screen<AddNoteView>() {
    override fun createView(context: android.content.Context) = com.wealthfront.magellan.kotlinsample.AddNoteView(context)

    override fun getTitle(context: android.content.Context?): String = "Add Note"
}


