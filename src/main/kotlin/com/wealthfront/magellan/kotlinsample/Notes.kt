package com.wealthfront.magellan.kotlinsample

import android.support.annotation.LayoutRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.wealthfront.magellan.kotlinsample.data.Note
import com.wealthfront.magellan.kotlinsample.data.SectionItem
import net.idik.lib.slimadapter.SlimAdapter

interface Notes {
    var onAddNote: UiCallback

    val slimAdapter: SlimAdapter

    companion object {
        @LayoutRes val layout = R.layout.notes_screen
    }
}

fun MagellanView<*>.displayNotes() = object : Notes {

    override var onAddNote: UiCallback by bindToClick(R.id.fab)

    override val slimAdapter: SlimAdapter = createAdapter()

    val recycler : RecyclerView get() = findViewById(R.id.recycler)

    fun createAdapter(): SlimAdapter {
        val slimAdapter = SlimAdapter.create()
                .register<SectionItem>(R.layout.home_item_section) { data: SectionItem, injector ->
                    injector.text(R.id.home_item_title, data.title)
                }
                .register<Note>(R.layout.home_item_card) { data: Note, injector ->
                    injector.text(R.id.home_item_title, data.title)
                            .text(R.id.home_item_description, data.description)
                            .clicked(R.id.card_view, { _ ->
                                (screen as NotesScreen).onItemClicked(data)
                            })
                }.attachTo(recycler)

        with(recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = slimAdapter
        }

        return slimAdapter
    }

}

data class TestNotes(
        override val slimAdapter: SlimAdapter,
        override var onAddNote: UiCallback = NOOP
) : Notes