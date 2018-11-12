package com.wealthfront.magellan.kotlinsample

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers

class ShowNoteRobot {
    fun isShown() = screenHasTitle("Note Detail")

    fun hasTitle(title: String) {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.note_detail_title),
                ViewMatchers.isDisplayed(),
                ViewMatchers.withText(title)
            )
        )
    }

    fun hasDescription(description: String) {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.note_detail_description),
                ViewMatchers.isDisplayed(),
                ViewMatchers.withText(description)
            )
        )
    }

    fun editNote(func: AddNoteRobot.() -> Unit): AddNoteRobot {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.note_detail_edit),
                ViewMatchers.isDisplayed()
            )
        ).perform(ViewActions.click())
        return AddNoteRobot().apply { isShown(); func() }
    }

    fun goBack(func: NoteRobot.() -> Unit): NoteRobot {
        Espresso.pressBack()
        return NoteRobot().apply { isShown(); func() }
    }
}