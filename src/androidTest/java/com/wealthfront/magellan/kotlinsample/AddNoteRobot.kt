package com.wealthfront.magellan.kotlinsample

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers

class AddNoteRobot  {
    fun isShown() {
        Espresso.onView(
            Matchers.allOf(
                Matchers.either(ViewMatchers.withText("Edit Note")).or(
                    ViewMatchers.withText("Add Note")
                ),
                ViewMatchers.isDescendantOfA(
                    ViewMatchers.withId(
                        R.id.action_bar
                    )
                )
            )
        )
    }

    fun isNewNote(isNew: Boolean) {
        val title = if (isNew) "Add Note" else "Edit Detail"
        screenHasTitle(title)
    }

    fun hasTitle(title: String) {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.add_note_title),
                ViewMatchers.withText(title),
                ViewMatchers.isDisplayed()
            )
        )
    }

    fun hasDescription(description: String) {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.add_note_description),
                ViewMatchers.withText(description),
                ViewMatchers.isDisplayed()
            )
        )
    }

    fun enterTitle(title: String) {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.add_note_title),
                ViewMatchers.isDisplayed()
            )
        ).perform(
            ViewActions.clearText(),
            ViewActions.typeText(title)
        )
    }

    fun enterDescription(description: String) {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.add_note_description),
                ViewMatchers.isDisplayed()
            )
        ).perform(
            ViewActions.clearText(),
            ViewActions.typeText(description)
        )
    }

    fun goBack(func: NoteRobot.() -> Unit): NoteRobot {
        Espresso.pressBack()
        return notes(func)
    }

    fun save(func: NoteRobot.() -> Unit): NoteRobot {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.add_note_save),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())
        return notes(func)
    }
}