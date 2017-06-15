package com.wealthfront.magellan.kotlinsample

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Screen


val BaseScreenView<*>.attach: Boolean
    get() = true

val BaseScreenView<*>.dontAttach: Boolean
    get() = false

fun BaseScreenView<*>.inflateViewFrom(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, true)


val BaseScreenView<*>.inflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun BaseScreenView<*>.toast(s: String) = Toast.makeText(context, s, Toast.LENGTH_SHORT).show()

fun BaseScreenView<*>.longToast(s: String) = Toast.makeText(context, s, Toast.LENGTH_LONG).show()

fun Screen<*>.toast(s: String) = (getView() as BaseScreenView<*>).toast(s)

