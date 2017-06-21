package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.kotlinsample.data.Note
import com.wealthfront.magellan.kotlinsample.data.SectionItem
import net.idik.lib.slimadapter.SlimAdapter


class NotesScreen : Screen<NotesView>() {

    override fun createView(context: Context) = NotesView(context)

    override fun getTitle(context: Context?) = "Notes"

    public override fun onShow(context: Context?) {
        view.onAddNote = this::addNote

        val loading = listOf(SectionItem("Loading, please wait"))
        view.slimAdapter.updateData(loading)

        App.Companion.repository.getNotes { notes: List<Note> ->
            view?.slimAdapter?.updateData(notes)
        }
    }

    fun addNote() = navigator.goTo(AddNoteScreen())

    fun onItemClicked(item: Note) {
        navigator.goTo(NoteDetailScreen(item.id))
    }

}


class NotesView(context: Context) : BaseScreenView<NotesScreen>(context) {

    var onAddNote: () -> Unit by bindToClick(R.id.fab)

    val slimAdapter: SlimAdapter

    init {
        inflate(context, R.layout.notes_screen, this)
        val recycler = findViewById(R.id.recycler) as RecyclerView

        slimAdapter = SlimAdapter.create()
                .register<SectionItem>(R.layout.home_item_section) { data: SectionItem, injector ->
                    injector.text(R.id.home_item_title, data.title)
                }
                .register<Note>(R.layout.home_item_card) { data: Note, injector ->
                    injector.text(R.id.home_item_title, data.title)
                            .text(R.id.home_item_description, data.description)
                            .clicked(R.id.card_view, { _ -> screen.onItemClicked(data) })
                }.attachTo(recycler)

        with(recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = slimAdapter
        }
    }


}