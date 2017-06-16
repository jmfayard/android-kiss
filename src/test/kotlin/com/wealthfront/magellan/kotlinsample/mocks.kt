package com.wealthfront.magellan.kotlinsample

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import net.idik.lib.slimadapter.SlimAdapter


fun NoteDetailScreen.mockWith(
        title: String = "",
        description: String = ""
): NoteDetailScreen = apply {
    navigator = mock()
    view = mock<NoteDetailView> {
        on(NoteDetailView::title) doReturn title
        on(NoteDetailView::description) doReturn description
    }
    onShow(mock())
}


fun AddNoteScreen.mockWith(
        title: String = "",
        description: String = ""
): AddNoteScreen = apply {
    navigator = mock()
    view = mock<AddNoteView> {
        on(AddNoteView::title) doReturn title
        on(AddNoteView::description) doReturn description
    }
    onShow(mock())
}

fun NotesScreen.mockWith(adapter: SlimAdapter): NotesScreen = apply {
    navigator = mock()
    view = mock<NotesView>() {
        on(NotesView::slimAdapter) doReturn adapter
    }
    onShow(mock())
}
