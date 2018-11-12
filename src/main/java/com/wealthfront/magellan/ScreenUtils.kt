@file:Suppress("unused")

package com.wealthfront.magellan

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import timber.log.Timber

val isRunningTest: Boolean by lazy { isRunningUnitTests || isRunningEspressoTests || isRunningOnFirebase }
val isRunningUnitTests: Boolean by lazy { classIsLoaded("org.junit.Assert") }
val isRunningEspressoTests: Boolean by lazy {
    classIsLoaded("android.support.test.espresso.Espresso")
}
val isRunningOnFirebase: Boolean by lazy {
    Settings.System.getString(MagellanScreen.applicationContext.contentResolver, "firebase.test.lab") == "true"
}

@ColorInt
fun MagellanScreen<*>.color(@ColorRes colorId: Int): Int =
    ContextCompat.getColor(
        MagellanScreen.applicationContext,
        colorId
    )

fun MagellanScreen<*>.toast(@StringRes message: Int, long: Boolean = false, vararg formatArgs: Any) {
    val i18nMessage: String = MagellanScreen.applicationContext.getString(message, *formatArgs)
    toast(i18nMessage, long)
}

/** screen.i18n(R.String.TAG_LOST) instead of cumbersome and unsafe screen!!.activity.getString(R.String.blahblah) **/
fun MagellanScreen<*>.i18n(@StringRes resId: Int, vararg formatArgs: String): String =
    MagellanScreen.applicationContext.getString(resId, *formatArgs)

fun MagellanScreen<*>.toast(message: String, long: Boolean = false) {
    Timber.i("toast($message)")
    if (isRunningTest) return
    val length = if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    if (message.isNotBlank()) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(MagellanScreen.applicationContext, message, length).show()
        }
    }
}

fun MagellanScreen<*>.hideSoftKeyboard(@IdRes editText: Int) {
    val widget = view?.findViewById<EditText>(editText)
    if (widget == null) {
        Timber.w("hideSoftKeyboard() failed, could not find EditText(id=0x${editText.toString(16)})")
    } else {
        val context = view?.context ?: return
        if (view != null && view.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.rootView.windowToken, 0) // InputMethodManager.SHOW_IMPLICIT
        }
    }
}

interface HasId {
    @get:IdRes
    val id: Int
}

fun MagellanScreen<*>.hideSoftKeyboard(editText: HasId) = hideSoftKeyboard(editText.id)

typealias UiCallback = () -> Unit

val NOOP: UiCallback = { Unit }

// https://stackoverflow.com/questions/28550370/how-to-detect-whether-android-app-is-running-ui-test-with-espresso
fun classIsLoaded(fullyQualifiedName: String): Boolean =
    try {
        Class.forName(fullyQualifiedName)
        true
    } catch (e: ClassNotFoundException) {
        false
    } catch (e: Throwable) {
        Timber.e(e, "Unexcted error in classExists()")
        false
    }