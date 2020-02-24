package de.fayard.android.kiss

import android.app.Activity
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import de.fayard.android.kiss.R
import com.wealthfront.magellan.support.SingleActivity
import timber.log.Timber

/** A [ScreenMarker] for a [MagellanScreen] that wish to use a SnackBar **/
interface HasSnackbar : ScreenMarker {
    val idCoordinatorLayout get() = R.id.coordinatorLayout

    fun snackbar(
        message: String,
        short: Boolean = false,
        isIndefinite: Boolean = false,
        block: Snackbar.() -> Unit = {}
    ) {
        val activity = SingleActivity.getNavigator().currentScreen().getActivity() ?: return
        val coordinatorLayout = coordinatorLayout(activity)
        Timber.i("snackbar($message)")
        val duration = when {
            isIndefinite -> Snackbar.LENGTH_INDEFINITE
            short -> Snackbar.LENGTH_SHORT
            else -> Snackbar.LENGTH_LONG // default
        }
        Snackbar.make(coordinatorLayout, message, duration)
            .apply {
                block()
                show()
            }
    }

    fun coordinatorLayout(activity: Activity): CoordinatorLayout {
        val layout: CoordinatorLayout? = activity.findViewById(idCoordinatorLayout)
        return layout ?: error(
            "Screen marked as HasSnackbar but I cannot find CoordinatorLayout with id 0x${idCoordinatorLayout.toString(
                16
            )}"
        )
    }
}