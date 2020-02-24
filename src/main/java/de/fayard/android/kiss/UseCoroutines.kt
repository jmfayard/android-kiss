package de.fayard.android.kiss

import android.content.Context
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/** A [ScreenMarker] for a [MagellanScreen] that wish to uses Coroutines **/
interface UseCoroutines : ScreenMarker, CoroutineScope {

    override fun setup(context: Context) {
        data["job"] = Job()
        data["handler"] = exceptionHandler()
    }

    override fun cleanup(context: Context) {
        val screenJob = data["job"] as? Job
        screenJob?.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + data["handler"] as CoroutineExceptionHandler + data["job"] as Job

    fun exceptionHandler() = CoroutineExceptionHandler { _, t ->
        Timber.e(t)
    }
}