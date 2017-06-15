package com.wealthfront.magellan.kotlinsample

import android.app.Application
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var ctx : Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        ctx = this

        Timber.plant(Timber.DebugTree())
    }
}
