package com.wealthfront.magellan.kotlinsample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.VisibleForTesting
import com.wealthfront.magellan.BaseScreenView
import com.wealthfront.magellan.Navigator
import com.wealthfront.magellan.Screen

typealias UiCallback = () -> Unit

val NOOP: UiCallback = {}

abstract class MagellanScreen<Display> : Screen<MagellanView<Display>>() {
    val display: Display? get() = testDisplay ?: view?.display

    private var testDisplay: Display? = null

    @VisibleForTesting
    fun setupForTests(display: Display, navigator: Navigator, activity: Activity) {
        this.navigator = navigator
        this.activity = activity
        this.testDisplay = display
        onShow(activity)
    }
}


@SuppressLint("ViewConstructor")
open class MagellanView<Display>(context: Context, @LayoutRes val layout: Int, setup: MagellanView<Display>.() -> Display)
    : BaseScreenView<MagellanScreen<Display>>(context) {
    val display: Display

    init {
        inflate(context, layout, this)
        display = setup()
    }
}