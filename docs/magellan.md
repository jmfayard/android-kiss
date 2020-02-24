
## Ditching activities and fragments

Android Activities get destroyed when you rotate the screen, and they
have a complex lifecycle. 

Using fragments is error-prone, and their lifecycle is even worse. 

This adds up a ton of overhead for the programmer.
How many time should be invested in this?
The right answer is it depends, and sometimes the added complexity is not worth it.

In those cases, it's worth looking at the Magellan navigation library

https://github.com/wealthfront/magellan

[Modern Android: Ditching Activities and Fragments](https://news.realm.io/news/sf-fabien-davos-modern-android-ditching-activities-fragments/)

Magellan was not really well suited for Kotlin when I adopted it so I tried to simplify it even further leveraging Kotlin features.

# Delegated properties

In a traditional MVP project, the views are often full of one-liner
methods that are just setting something on layout or checking some
properties

```kotlin
override fun getPassword(): String {
  return passwordView.text.toString()
}
override fun setPassword(password: String) {
  passwordView.text = name
}
```

Marcin Moskala had the insight that all those functions can be
abstracted by a kotlin property `var password: String`, with a getter
and a setter that could be delegated to a library.

* a TextView can be abstracted by `var label: String`
* an EditText can be abstracted by `var input: String`
* a view visibility can be abstracted by `var loading: Boolean`
* an onclick Listener can be abstracted by `var onSubmit: () -> Unit`

Check out
https://github.com/MarcinMoskala/KotlinAndroidViewBindings[KotlinAndroidViewBindings]

## My take on this


I leveraged this pattern for Magellan with two base classes
`MagellanView<Display>` and `MagellanScreen<Display>` where `<Display>`
is any interface with a bag of properties, below `AddNote`

Our `AddNote` is available inside `AddNotesScreen` via the `display`
(nullable) property.

```kotlin
interface AddNote {
    var title: String

    var description: String

    // UiCallback, a `typealias` for () -> Unit, is what you use for an onClickListener
    var onSubmit: UiCallback
}

class AddNoteScreen(val noteId: String? = null)
: MagellanScreen<AddNote>(
        screenLayout = R.layout.addnote_screen,
        screenTitle = if (noteId != null) R.string.title_editnote else R.string.title_addnote,
        screenSetup = FrameLayout::displayAddNote
)
```

As you may notice, we don't define our own subclass for the view.
Instead

* an extension function `displayAddNote()` creates an `AddNote` backed
by delegated properties
* a fake implementation of `AddNote` is created by a simple data class

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