package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.support.annotation.StringRes
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun toast(message: String, long: Boolean = false) {
    if (isRunningTest) return
    val length = if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    // use the applicationContext so that the toast is shown even if the screen is not in the foreground
    Toast.makeText(applicationContext(), message, length).show()
}

fun toast(@StringRes message: Int, long: Boolean = false, vararg formatArgs: Any) {
    val i18nMessage = applicationContext().getString(message, *formatArgs)
    toast(i18nMessage, long)
}

private fun applicationContext() = App.instance

fun View?.hideKeyboard() {
    if (this != null) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }
}

val isRunningTest: Boolean by lazy {
    //    val firebaseTestLab = "true" == Settings.System.getString(App.instance.contentResolver, "firebase.test.lab")
    classExists("junit.framework.Test")
}

// https://stackoverflow.com/questions/28550370/how-to-detect-whether-android-app-is-running-ui-test-with-espresso
fun classExists(fullyQualifiedName: String): Boolean =
    try {
        Class.forName(fullyQualifiedName)
        true
    } catch (e: ClassNotFoundException) {
        false
    }