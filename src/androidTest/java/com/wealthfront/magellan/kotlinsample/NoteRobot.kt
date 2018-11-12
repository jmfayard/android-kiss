package com.wealthfront.magellan.kotlinsample

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers

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
fun notes(func: NoteRobot.() -> Unit) = NoteRobot().apply(func)

@Suppress("NOTHING_TO_INLINE")
inline fun screenHasTitle(title: String) {
    Espresso.onView(
        Matchers.allOf(
            ViewMatchers.withText(title),
            ViewMatchers.isDescendantOfA(ViewMatchers.withId(R.id.action_bar))
        )
    )
}
class NoteRobot  {
    fun isShown() {}

    var position: Int = -1

    fun selectNote(position: Int) {
        this.position = position
    }

    fun showNote(func: ShowNoteRobot.() -> Unit): ShowNoteRobot {
        check(position != -1) { "NoteRobot.selectNote() must be called before showNote" }
        val recyclerView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.recycler),
                ViewMatchers.isDisplayed()
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ViewActions.click()
            )
        )
        return ShowNoteRobot().apply { isShown(); func() }
    }

    fun addNote(func: AddNoteRobot.() -> Unit): AddNoteRobot {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.fab),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())
        return AddNoteRobot().apply(func)
    }
}