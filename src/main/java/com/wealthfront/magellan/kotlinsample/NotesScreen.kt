package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.widget.FrameLayout
import kotlinx.coroutines.launch


class NotesScreen(
        val repository: NotesRepository = InMemoryRepository
) : MagellanScreen<Notes>(
        R.layout.notes_screen, R.string.title_notes, FrameLayout::displayNotes
) {

    public override fun onShow(context: Context) {
        super.onShow(context)
        setupUx()

        launch {
            display?.updateRecyclerViewData(LoadingList)
            val notes = repository.getNotes()
            display?.updateRecyclerViewData(notes)
        }
    }

    private fun setupUx() {
        display?.onAddNote = { addNote() }
        display?.setupRecyclerView(this::onItemClicked)

    }

    fun addNote() = navigator.goTo(AddNoteScreen())

    fun onItemClicked(item: Note) {
        navigator.goTo(NoteDetailScreen(item.id))
    }

}
