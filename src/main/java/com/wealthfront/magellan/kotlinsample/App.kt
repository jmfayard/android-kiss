package com.wealthfront.magellan.kotlinsample

import android.app.Application
import android.support.multidex.MultiDex
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Timber.plant(Timber.DebugTree())

        MultiDex.install(this);
    }
}
