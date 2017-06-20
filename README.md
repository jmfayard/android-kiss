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

Views
------

In a traditional MVP project, the views are often full of one-liner methods that are just setting something on layout or checking some properties

```kotlin
override fun getDescription(): String {
    return addNoteDescription.text.toString()
}
override fun setDescription(description: String) {
    addNoteDescription.text = description
}
```

A nice approach to avoid to avoid this boilerplate in kotlin was recommanded and implented by [Marcin Moskala](http://marcinmoskala.com/android/kotlin/2017/05/05/still-mvp-or-already-mvvm.html)

it consists of using [Delegated Properties](https://kotlinlang.org/docs/reference/delegated-properties.html)

```kotlin
class AddNoteView(context: Context) : BaseScreenView<AddNoteScreen>(context) {
    // ....
    var description : String by addNoteDescription.bindToEditText()
    var title       : String by addNoteTitle.bindToEditText()
    var onSubmit    : () -> Unit by addNoteSave.bindToClick()
}
```

The API of our Magellan View is now as simple and stupid as it should be:

```kotlin
class AddNoteScreen() : Screen<AddNoteView>() {

    override public fun onShow(context: Context) {
        view.title = "Initial Title"
        view.description = "Initial Description"
        view.onSubmit = {
            submitNewNote( view.title, view.description )
        }
    }
}
```

Unit Tests
----------

The sample app shows how straightforward it can be to write unit tests for our Magellan `Screen`

Notes:

- we are using the useful wrapper [mockito-kotlin](https://github.com/nhaarman/mockito-kotlin)
- thanks to [this configuration](https://github.com/jmfayard/android-kotlin-magellan/blob/master/src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker), Mockito can mock our kotlin classes (final by default)
- mocking a magellan `Screen` consist of mocking the Magellan `Navigator`, mocking the `View`, also providing return values for our delegated properties (`var description: String`, `var title: String`), and then calling `Screen.onShow()`. See `mocks.kt`

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


