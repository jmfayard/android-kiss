package com.wealthfront.magellan.kotlinsample

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import com.marcinmoskala.kotlinandroidviewbindings.bindToClick
import com.wealthfront.magellan.kotlinsample.data.ListItem
import com.wealthfront.magellan.kotlinsample.data.Note
import com.wealthfront.magellan.kotlinsample.data.SectionItem
import net.idik.lib.slimadapter.SlimAdapter

interface Notes : IDisplay {
    var onAddNote: UiCallback

    fun setupRecyclerView(onclick: (Note) -> Unit)
    fun updateRecyclerViewData(items: List<ListItem>)
}

fun FrameLayout.displayNotes() = object : Notes {

    lateinit var slimAdapter: SlimAdapter

    override var onAddNote: UiCallback by bindToClick(R.id.fab)

    override fun setupRecyclerView(onclick: (Note) -> Unit) {
        val recyclerView: RecyclerView = findViewById(R.id.recycler)
        slimAdapter = createAdapter(onclick).attachTo(recyclerView)
        recyclerView.adapter = slimAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun updateRecyclerViewData(items: List<ListItem>) {
        slimAdapter.updateData(items)
    }


    val recycler: RecyclerView get() = findViewById(R.id.recycler)

    fun createAdapter(onclick: (Note) -> Unit): SlimAdapter {
        val slimAdapter = SlimAdapter.create()
                .register(R.layout.home_item_section) { data: SectionItem, injector ->
                    injector.text(R.id.home_item_title, data.title)
                }
                .register(R.layout.home_item_card) { data: Note, injector ->
                    injector.text(R.id.home_item_title, data.title)
                            .text(R.id.home_item_description, data.description)
                            .clicked(R.id.card_view) {
                                onclick(data)
                            }
                }.attachTo(recycler)

        with(recycler) {
            layoutManager = LinearLayoutManager(context)
            adapter = slimAdapter
        }

        return slimAdapter
    }

}