package com.wealthfront.magellan.kotlinsample

import io.kotlintest.matchers.shouldBe


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

fun exampleRobotTest() {
    notes {
        nbItems() shouldBe 2
    } addNote {
        isNewNote() shouldBe true
        enterTitle("Retrofit")
        enterDescription("Rest API for Java and Android")
    } save {
        require(nbItems() == 3)
        selectNote(3)
    } showNote {
        title() shouldBe "Retrofit"
        description() shouldBe "Rest API for Java and Android"
    } editNote {
        isNewNote() shouldBe false
        enterDescription("A type-safe HTTP client for Android and Java")
    } save {
        nbItems() shouldBe 3
        val (_, description) = selectNote(3)
        description shouldBe "A type-safe HTTP client for Android and Java"
        selectNote(3)
    } showNote {

    } goBack {
        nbItems() shouldBe 3
    }
}


fun notes(func: NotesRobot.() -> Unit) = EspressoNotesRobot().apply(func)

interface NotesRobot {
    fun nbItems() : Int
    fun selectNote(i: Int) : NoteData

    infix fun showNote(func: ShowNoteRobot.() -> Unit) : ShowNoteRobot
    infix fun addNote(func: AddNoteRobot.() -> Unit) : AddNoteRobot
}

interface ShowNoteRobot {
    fun title() : String
    fun description() : String

    infix fun editNote(func: AddNoteRobot.() -> Unit) : AddNoteRobot
    infix fun goBack(func: NotesRobot.() -> Unit) : NotesRobot
}

interface AddNoteRobot {
    fun title() : String
    fun description() : String
    fun isNewNote() : Boolean
    fun enterTitle(title: String)
    fun enterDescription(title: String)

    infix fun goBack(func: NotesRobot.() -> Unit) : NotesRobot
    infix fun save(func: NotesRobot.() -> Unit) : NotesRobot
}

typealias NoteData = Pair<String, String>