package com.wealthfront.magellan.kotlinsample

import android.app.Activity
import android.content.Context
import com.wealthfront.magellan.Navigator
import com.wealthfront.magellan.kotlinsample.data.Note
import com.wealthfront.magellan.kotlinsample.data.NotesServiceApiImpl
import net.idik.lib.slimadapter.SlimAdapter

val mockNavigator: Navigator get() = io.kotlintest.mock.mock()
val mockActivity: Activity get() = io.kotlintest.mock.mock()
val mockContext: Context get() = io.kotlintest.mock.mock()
val mockAdapter: SlimAdapter = io.kotlintest.mock.mock()


fun fetchNotes(): List<Note> {
    return NotesServiceApiImpl.NOTES_SERVICE_DATA.values.toList()
}


