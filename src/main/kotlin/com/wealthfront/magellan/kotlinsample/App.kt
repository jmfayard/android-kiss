package com.wealthfront.magellan.kotlinsample

import android.app.Application
import android.support.multidex.MultiDex
import com.wealthfront.magellan.kotlinsample.data.InMemoryRepository
import com.wealthfront.magellan.kotlinsample.data.NotesRepository
import com.wealthfront.magellan.kotlinsample.data.NotesServiceApiImpl
import timber.log.Timber

class App : Application() {



    companion object {
        val repository: NotesRepository by lazy {
            InMemoryRepository(NotesServiceApiImpl())
        }

        lateinit var instance : App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Timber.plant(Timber.DebugTree())

        MultiDex.install(this);

    }
}
