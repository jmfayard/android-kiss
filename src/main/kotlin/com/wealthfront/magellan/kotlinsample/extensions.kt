package com.wealthfront.magellan.kotlinsample

import android.content.Context
import android.provider.Settings
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen



fun BaseScreenView<*>.inflateViewFrom(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, true)

val BaseScreenView<*>.inflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun BaseScreenView<*>.toast(s: String) {
    if (context != null) Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}

fun BaseScreenView<*>.longToast(s: String) {
    if (context != null) Toast.makeText(context, s, Toast.LENGTH_LONG).show()
}

fun Screen<*>.toast(s: String) = (getView() as BaseScreenView<*>).toast(s)
fun Screen<*>.longToast(s: String) = (getView() as BaseScreenView<*>).longToast(s)

fun View?.hideKeyboard() {
    if (this != null) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }
}



val isRunningTest: Boolean by lazy {
    val firebaseTestLab = Settings.System.getString(App.instance.contentResolver, "firebase.test.lab")
    classExists("junit.framework.Test") || firebaseTestLab == "true"
}

// https://stackoverflow.com/questions/28550370/how-to-detect-whether-android-app-is-running-ui-test-with-espresso
fun classExists(fullyQualifiedName: String) : Boolean =
        try {
            Class.forName(fullyQualifiedName)
            true
        } catch (e: ClassNotFoundException) {
            false
        }