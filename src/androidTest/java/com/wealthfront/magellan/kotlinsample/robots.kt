package com.wealthfront.magellan.kotlinsample

/**
 * Define a DSL for our Instrumentation Testing Robots
 *
 * It allows us to write tests that give an high level view of
 *    WHAT we want to achieve
 * and to keep that separate from
 *    HOW each individual step is accomplished (see espresso.kt)
 *
 * See https://news.realm.io/news/kau-jake-wharton-testing-robots/
 *
 */

fun notes(func: NotesRobot.() -> Unit) = EspressoNotesRobot().apply(func)

/** See [EspressoNotesRobot] **/
interface NotesRobot {
    fun isShown()
    infix fun addNote(func: AddNoteRobot.() -> Unit): AddNoteRobot

    fun selectNote(position: Int) // must be called before showNote
    infix fun showNote(func: ShowNoteRobot.() -> Unit): ShowNoteRobot
}

/** See [EspressoShowNoteRobot] **/
interface ShowNoteRobot {
    fun isShown()
    fun hasTitle(title: String)
    fun hasDescription(description: String)

    infix fun editNote(func: AddNoteRobot.() -> Unit): AddNoteRobot
    infix fun goBack(func: NotesRobot.() -> Unit): NotesRobot
}

/** See [EspressoAddNoteRobot] **/
interface AddNoteRobot {
    fun isShown()
    fun hasTitle(title: String)
    fun hasDescription(description: String)
    fun isNewNote(isNew: Boolean)
    fun enterTitle(title: String)
    fun enterDescription(description: String)

    infix fun goBack(func: NotesRobot.() -> Unit): NotesRobot
    infix fun save(func: NotesRobot.() -> Unit): NotesRobot
}

