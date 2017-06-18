package com.wealthfront.magellan.kotlinsample

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.either

/** TODO: Espresso implementation of the DSL **/

class EspressoNotesRobot : NotesRobot {
    override fun isShown() {

    }

    var position: Int = -1


    override fun selectNote(position: Int) {
        this.position = position
    }

    override fun showNote(func: ShowNoteRobot.() -> Unit): ShowNoteRobot {
        check(position != -1) { "EspressoNotesRobot.selectNote() must be called before showNote" }
        val recyclerView = onView(
                allOf(withId(R.id.recycler), isDisplayed()))
        recyclerView.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
        return EspressoShowNoteRobot().apply { isShown() ; func() }
    }

    override fun addNote(func: AddNoteRobot.() -> Unit): AddNoteRobot {
        onView(allOf(
                withId(R.id.fab),
                isDisplayed()
        )).perform(click())
        return EspressoAddNoteRobot().apply(func)
    }
}

class EspressoShowNoteRobot : ShowNoteRobot {
    override fun isShown() = screenHasTitle("Note Detail")

    override fun hasTitle(title: String) {
        onView(allOf(
                withId(R.id.note_detail_title), isDisplayed(), withText(title)
        ))
    }

    override fun hasDescription(description: String) {
        onView(allOf(
                withId(R.id.note_detail_description), isDisplayed(), withText(description)
        ))
    }


    override fun editNote(func: AddNoteRobot.() -> Unit): AddNoteRobot {
        onView(
                allOf(withId(R.id.note_detail_edit), isDisplayed())
        ).perform(click())
        return EspressoAddNoteRobot().apply { isShown() ; func() }
    }

    override fun goBack(func: NotesRobot.() -> Unit): NotesRobot {
        onView(withContentDescription("Navigate up")).perform(click())
        return EspressoNotesRobot().apply { isShown() ; func() }
    }

}

class EspressoAddNoteRobot : AddNoteRobot {
    override fun isShown() {
        onView(allOf(
                either(withText("Edit Note")).or(withText("Add Note")),
                isDescendantOfA(withId(R.id.action_bar))))
    }

    override fun isNewNote(isNew: Boolean) {
        val title = if (isNew) "Add Note" else "Edit Detail"
        screenHasTitle(title)
    }

    override fun hasTitle(title: String) {
        onView(allOf(
                withId(R.id.add_note_title),
                withText(title),
                isDisplayed()
        ))

    }

    override fun hasDescription(description: String) {
        onView(allOf(
                withId(R.id.add_note_description),
                withText(description),
                isDisplayed()
        ))
    }

    override fun enterTitle(title: String) {
        onView(
                allOf(withId(R.id.add_note_title), isDisplayed()
                )).perform(clearText(), typeText(title))
    }

    override fun enterDescription(description: String) {
        onView(
                allOf(withId(R.id.add_note_description), isDisplayed())
        ).perform(clearText(), typeText(description))
    }

    override fun goBack(func: NotesRobot.() -> Unit): NotesRobot {
        onView(withContentDescription("Navigate up")).perform(click())
        return notes(func)
    }

    override fun save(func: NotesRobot.() -> Unit): NotesRobot {
        onView(
                allOf(withId(R.id.add_note_save), isDisplayed())
        ).perform(click())
        return notes(func)
    }

}

@Suppress("NOTHING_TO_INLINE")
private inline fun screenHasTitle(title: String) {
    onView(allOf(withText(title), isDescendantOfA(withId(R.id.action_bar))))
}