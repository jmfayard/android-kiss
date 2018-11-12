package com.wealthfront.magellan

import android.app.Activity
import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.annotation.VisibleForTesting

/** See [MagellanScreen], [MagellanView] **/
interface IDisplay

/**
 * Base Magellan Screen.
 *
 * Features that are optionals but common to a group of screens are implemented via a [ScreenMarker]
 *
 * It's good for the developer's sanity to become more independent from the Android framework,
 * which is why instead of managing a ScreenView,
 * you define a Display with properties of basic types
 *
 * ```
 * interface MyDisplay : IDisplay {
 *     var label : String
 * }
 *
 * class MyScreen()
 *     : MagellanScreen<MyDisplay>(R.layout.notes_screen, R.string.title_notes, ScreenSetups::setupMyScreen)
 *     , BackGoesToRoot
 * {
 *     // getTitle() is already defined by MagellanView
 *     // createView()  is already defined by MagellanView
 *
 *     override fun onShow(context: Context) {
 *         super.onShow(context)
 *         display?.label = "Hello World"
 *     }
 * }
 *
 * fun ScreenSetups.setupMyScreen() = object  : MyDisplay {
 *
 *     override var label: String by bindToText(R.id.text)
 *     // See https://github.com/MarcinMoskala/KotlinAndroidViewBindings
 * }
 *```
 ***/
abstract class MagellanScreen<Display : IDisplay>(
    @LayoutRes val screenLayout: Int,
    @StringRes val screenTitle: Int,
    val screenSetup: MagellanView<Display>.() -> Display
) : Screen<MagellanView<Display>>() {

    companion object {
        lateinit var applicationContext: Context // Must be initialized in Application.onCreate()
    }

    final override fun getTitle(context: Context): String {
        return context.getString(screenTitle)
    }

    override fun createView(context: Context) =
        MagellanView(context, screenLayout, screenSetup)

    @CallSuper
    override fun onShow(context: Context) {
        if (this is UseCoroutines) this.setup(context)
    }

    @CallSuper
    override fun onHide(context: Context) {
        if (this is UseCoroutines) this.cleanup(context)
    }

    val display: Display? get() = testDisplay ?: view?.display

    private var testDisplay: Display? = null

    /** Used by a [ScreenMarker] to store data **/
    val markersData: MutableMap<String, Any> = mutableMapOf()

    fun safeShowDialog(dialog: DialogCreator) {
        this.dialog?.run {
            setOnDismissListener(null)
            dismiss()
        }
        if (activity != null) {
            showDialog(dialog)
        }
    }

    /*** Used in unit tests. Calls onShow(). [display] should be a data class implementing Display. [navigator] and [activity] are mocked objects ***/
    @VisibleForTesting
    fun setupForTests(display: Display, navigator: Navigator, activity: Activity): Display {
        this.navigator = navigator
        this.activity = activity
        this.testDisplay = display
        onShow(activity)
        return display
    }

    override fun handleBack(): Boolean {
        if (this is BackGoesToRoot) {
            navigator.goBackToRoot(NavigationType.GO)
            return true
        }
        return super.handleBack()
    }
}



