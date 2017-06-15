package com.wealthfront.magellan.kotlinsample

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock


fun NoteDetailScreen.withMockedView(
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