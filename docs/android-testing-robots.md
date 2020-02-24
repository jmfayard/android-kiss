Jake Wharton proposed a very nice pattern for writing instrumentation
tests in Kotlin :
https://news.realm.io/news/kau-jake-wharton-testing-robots/[Testing
Robots]

Each screen is associated with a `Robot` that provides methods
corresponding to WHAT can be done on a given screen

```
fun notes(func: NotesRobot.() -> Unit) = EspressoNotesRobot().apply(func)

interface NotesRobot {
    infix fun addNote(func: AddNoteRobot.() -> Unit): AddNoteRobot
    fun selectNote(position: Int)
    infix fun showNote(func: ShowNoteRobot.() -> Unit): ShowNoteRobot
}
```

Those Robots combine in a nice, declarative DSL that describes `WHAT`
the test should do, very much like a human tester would do

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

Each robot knows `HOW` to perform each basic step, for example
`showNote()` is currently implemented like this

```
override fun addNote(func: AddNoteRobot.() -> Unit): AddNoteRobot {
    onView( allOf( withId(R.id.fab), isDisplayed() ) ).perform(click())
    return EspressoAddNoteRobot().apply(func)
}
```

If tomorrow instead of using a `FloatingActionButton` we were to use an
entry in the menu, we would just have to change this method and leave
our test intact.

See the
https://github.com/jmfayard/android-kotlin-magellan/tree/master/src/androidTest/java/com/wealthfront/magellan/kotlinsample[Instrumentation
Tests]

Click on the screencast below to see what the above test does

https://www.youtube.com/watch?v=sqtlAL8f2YU[image:https://user-images.githubusercontent.com/459464/27325147-c8a881ae-55a7-11e7-93f8-ba83701cb3a4.png[Screen
Cast]]
