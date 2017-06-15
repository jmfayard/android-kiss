package com.wealthfront.magellan.kotlinsample


class NotesScreen : com.wealthfront.magellan.Screen<com.wealthfront.magellan.kotlinsample.NotesView>() {

    override fun createView(context: android.content.Context) = com.wealthfront.magellan.kotlinsample.NotesView(context)

    override fun getTitle(context: android.content.Context?) = "Notes"

    override fun onShow(context: android.content.Context?) {
        val loading = listOf(com.wealthfront.magellan.kotlinsample.data.SectionItem("Loading, please wait"))
        view.slimAdapter.updateData(loading)
        com.wealthfront.magellan.kotlinsample.App.Companion.repository.getNotes { notes: List<com.wealthfront.magellan.kotlinsample.data.Note> ->
            view?.slimAdapter?.updateData(notes)
        }
    }

    fun onItemClicked(item: com.wealthfront.magellan.kotlinsample.data.Note) {
        navigator.goTo(com.wealthfront.magellan.kotlinsample.screens.NoteDetailScreen(item.id))
    }

}


class NotesView(context: android.content.Context) : com.wealthfront.magellan.BaseScreenView<NotesScreen>(context) {



    val slimAdapter by lazy {
        net.idik.lib.slimadapter.SlimAdapter.create()
                .register<com.wealthfront.magellan.kotlinsample.data.SectionItem>(com.wealthfront.magellan.kotlinsample.R.layout.home_item_section) { data: com.wealthfront.magellan.kotlinsample.data.SectionItem, injector ->
                    injector.text(com.wealthfront.magellan.kotlinsample.R.id.home_item_title, data.title)
                }
                .register<com.wealthfront.magellan.kotlinsample.data.Note>(com.wealthfront.magellan.kotlinsample.R.layout.home_item_card) { data: com.wealthfront.magellan.kotlinsample.data.Note, injector ->
                    injector.text(com.wealthfront.magellan.kotlinsample.R.id.home_item_title, data.title)
                            .text(com.wealthfront.magellan.kotlinsample.R.id.home_item_description, data.description)
                            .clicked(com.wealthfront.magellan.kotlinsample.R.id.card_view, { _ -> screen.onItemClicked(data) })
                }
    }

    init {
        inflateViewFrom(com.wealthfront.magellan.kotlinsample.R.layout.home_screen)
        val list = findViewById(com.wealthfront.magellan.kotlinsample.R.id.recycler) as android.support.v7.widget.RecyclerView

        with(list) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            adapter = slimAdapter.attachTo(list)
        }
    }


}