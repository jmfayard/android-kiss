package com.wealthfront.magellan.kotlinsample.screens

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.wealthfront.magellan.kotlinsample.R
import com.wealthfront.magellan.kotlinsample.inflateViewFrom
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen
import net.idik.lib.slimadapter.SlimAdapter

interface ListItem

data class HomeItem(val title: String, val description: String, val screen: Screen<*>) : ListItem

data class HeaderItem(val title: String) : ListItem


class HomeScreen : com.wealthfront.magellan.Screen<HomeView>() {

    override fun createView(context: android.content.Context) = HomeView(context)

    override fun getTitle(context: android.content.Context?) = "Ok Android"

    fun onItemClicked(item: HomeItem) {
        navigator.goTo(item.screen)
    }

    val ITEMS: List<ListItem> by lazy {
        listOf(
                HomeItem("Add note", "add", AddNoteScreen()),
                HomeItem("NoteDetail", "detail", NoteDetailScreen())
        )
    }

    override fun onShow(context: Context?) {
        view.slimAdapter.updateData(ITEMS)
    }

}


class HomeView(context: android.content.Context) : BaseScreenView<HomeScreen>(context) {



    val slimAdapter by lazy {
        SlimAdapter.create()
                .register<HomeItem>(R.layout.home_item_card) { data: HomeItem, injector ->
                    injector.text(R.id.home_item_title, data.title)
                            .text(R.id.home_item_description, data.description)
                            .clicked(R.id.card_view, { _ -> screen.onItemClicked(data) })
                }
    }

    init {
        inflateViewFrom(R.layout.home_screen)
        val list = findViewById(R.id.recycler) as RecyclerView

        with(list) {
            layoutManager = LinearLayoutManager(context)
            adapter = slimAdapter.attachTo(list)
        }
    }


}