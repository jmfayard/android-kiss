package com.wealthfront.magellan.kotlinsample

/** TODO: Espresso implementation of the DSL **/

class EspressoNotesRobot : NotesRobot {
    override fun nbItems(): Int = 2

    override fun selectNote(i: Int) = "title" to "description"

    override fun showNote(func: ShowNoteRobot.() -> Unit): ShowNoteRobot
        = EspressoShowNoteRobot().apply(func)

    override fun addNote(func: AddNoteRobot.() -> Unit): AddNoteRobot
        = EspressoAddNoteRobot().apply(func)
}

class EspressoShowNoteRobot : ShowNoteRobot {
    override fun title(): String = "title"

    override fun description(): String = "description"

    override fun editNote(func: AddNoteRobot.() -> Unit): AddNoteRobot {
        return EspressoAddNoteRobot().apply(func)
    }

    override fun goBack(func: NotesRobot.() -> Unit): NotesRobot {
        return EspressoNotesRobot().apply(func)
    }

}

class EspressoAddNoteRobot : AddNoteRobot {
    override fun title(): String {
        return "title"
    }

    override fun description(): String {
        return "description"
    }

    override fun isNewNote(): Boolean {
        return true
    }

    override fun enterTitle(title: String) {

    }

    override fun enterDescription(title: String) {

    }

    override fun goBack(func: NotesRobot.() -> Unit): NotesRobot {
        return notes(func)
    }

    override fun save(func: NotesRobot.() -> Unit): NotesRobot {
        return notes(func)
    }

}