Reimplementation of the [android-testing codelab](https://github.com/googlecodelabs/android-testing) with Kotlin and [Magellan](https://github.com/wealthfront/magellan).

   <small>*Click to view the screencast*</small>

[![Screen Cast](https://user-images.githubusercontent.com/459464/27325147-c8a881ae-55a7-11e7-93f8-ba83701cb3a4.png)](https://www.youtube.com/watch?v=sqtlAL8f2YU)



Ditching activities and fragments
-------

Andriod Activities get destroyed when you rotate the screen, and they have a complex lifecycle. Using fragments is error-prone, and their lifecycle is even worse. How would you like a screen that doesn’t get destroyed on rotation, and a navigation that’s as simple as calling `goTo(AddNotesScreen())`?

This sample app is based on [Magellan navigation library](https://github.com/wealthfront/magellan) which provides just that

If you are not familiar with Magellan, read first: [Modern Android: Ditching Activities and Fragments](https://news.realm.io/news/sf-fabien-davos-modern-android-ditching-activities-fragments/)

Written in Kotlin
-----

This sample is written in [Kotlin](kotlinlang.org) and shows additional techniques to make writing views even simpler, and make both unit testing and instrumentation tests straightforward (as it should be for a reimplementation of the android-testing codelab)

Delegated Properties
--------------------

In a traditional MVP project, the views are often full of one-liner methods that are just setting something on layout or checking some properties

```kotlin
override fun getPassword(): String {
  return passwordView.text.toString()
}
override fun setPassword(password: String) {
  passwordView.text = name
}
```

Marcin Moskala had the insight that all those functions can be abstracted by a kotlin property `var password: String`,
with a getter and a setter that could be delegated to a library.

- a TextView can be abstracted by `var label: String`
- an EditText can be abstracted by `var input: String`
- a view visibility can be abstracted by `var loading: Boolean`
- an onclick Listener can be abstracted by `var onSubmit: () -> Unit`

Check out [KotlinAndroidViewBindings](https://github.com/MarcinMoskala/KotlinAndroidViewBindings)

In practice
-----------

I leveraged this pattern for Magellan with two base classes `MagellanView<Display>` and `MagellanScreen<Display>`
where `<Display>` is any interface with a bag of properties, below `AddNote`

Our `AddNote` is available inside `AddNotesScreen` via the `display` (nullable) property.


```
interface AddNote {
    var title: String

    var description: String

    // UiCallback, a `typealias` for () -> Unit, is what you use for an onClickListener
    var onSubmit: UiCallback
}

class AddNoteScreen() : MagellanScreen<AddNote>() {

    override fun createView(context: Context) : MagellanView<AddNote>
        = MagellanView(context, R.layout.addnote_screen, FrameLayout::displayAddNote)

    override public fun onShow(context: Context?) {
        display?.title = "Set an initial title"
        display?.description = "Set an initial description"
        display?.onSubmit = {
            doSomethingWith(display?.title, display?.description)
        }
    }
}
```



As you may notice, we don't define our own subclass for the view. Instead

- an extension function `displayAddNote()` creates an `AddNote` backed by delegated properties
- a fake implementation of `AddNote` is created by a simple data class

```kotlin

fun FrameLayout.displayAddNote() = object : AddNote {
    override var onSubmit    : UiCallback by bindToClick(R.id.add_note_save)
    override var title       : String     by bindToEditText(R.id.add_note_title)
    override var description : String     by bindToEditText(R.id.add_note_description)
}

data class TestAddNote(
    override var title: String,
    override var description: String,
    override var onSubmit: UiCallback = NOOP
) : AddNote
```




Unit Tests
----------

Using the above strategy, unit testing is super simple because we rely very little on mocking (except for getNavigator() and getActivity())

Instead we have our `data class TestAddNote` that is trivial to test

```

@Test fun editExistingNote() {
    val note = notes.first()
    val screen = AddNoteScreen(noteId = note.id)
    screen.setupForTests(AddNote.TEST, mockNavigator, mockActivity)

    val display = requireNotNull(screen.display)
    display.title shouldBe note.title
    display.description shouldBe note.description
}
```


See the [Unit Tests](https://github.com/jmfayard/android-kotlin-magellan/tree/master/src/test/kotlin/com/wealthfront/magellan/kotlinsample)


Testing Robots
-----

Jake Wharton proposed a very nice pattern for writing instrumentation tests in Kotlin : [Testing Robots ](https://news.realm.io/news/kau-jake-wharton-testing-robots/)

Each screen is associated with a `Robot` that provides methods corresponding to WHAT can be done on a given screen

```kotlin
fun notes(func: NotesRobot.() -> Unit) = EspressoNotesRobot().apply(func)

interface NotesRobot {
    infix fun addNote(func: AddNoteRobot.() -> Unit): AddNoteRobot
    fun selectNote(position: Int)
    infix fun showNote(func: ShowNoteRobot.() -> Unit): ShowNoteRobot
}
```


Those Robots combine in a nice, declarative DSL that describes `WHAT` the test should do, very much like a human tester would do

```kotlin
    @Test
    fun testingRobot() {

        notes {

        } addNote {
            hasTitle("")
            hasDescription("")
            enterTitle("Just Going back")
            hasTitle("Just Going back")
        } goBack {

        } addNote {
            hasTitle("")
            hasDescription("")
            enterTitle("Testing Robots")
            enterDescription("The Robots Pattern allows to separate WHAT a test should verify from HOW the verifications are implemented")
        } save {
            selectNote(position = 2)
        } showNote {
            hasTitle("Testing Robots")
        } editNote {
            hasTitle("Testing Robots")
            enterDescription("... give it a try!")
        } save {

        }
    }
```



Each robot knows `HOW` to perform each basic step, for example `showNote()` is currently implemented like this

```
    override fun addNote(func: AddNoteRobot.() -> Unit): AddNoteRobot {
        onView( allOf( withId(R.id.fab), isDisplayed() ) ).perform(click())
        return EspressoAddNoteRobot().apply(func)
    }
```

If tomorrow instead of using a `FloatingActionButton` we were to use an entry in the menu, we would just have to change this method and leave our test intact.

See the [Instrumentation Tests](https://github.com/jmfayard/android-kotlin-magellan/tree/master/src/androidTest/java/com/wealthfront/magellan/kotlinsample)

Click on the screencast below to see what the above test does

[![Screen Cast](https://user-images.githubusercontent.com/459464/27325147-c8a881ae-55a7-11e7-93f8-ba83701cb3a4.png)](https://www.youtube.com/watch?v=sqtlAL8f2YU)


