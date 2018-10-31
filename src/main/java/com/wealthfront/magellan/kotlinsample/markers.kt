package com.wealthfront.magellan.kotlinsample

import android.support.annotation.IdRes

interface IDisplay

typealias UiCallback = () -> Unit

val NOOP: UiCallback = { Unit }

interface HasId {
    @get:IdRes
    val id: Int
}

interface BackGoesToRoot