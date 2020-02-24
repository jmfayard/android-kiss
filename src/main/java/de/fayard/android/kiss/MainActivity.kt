package de.fayard.android.kiss

import android.os.Bundle
import android.view.MenuItem
import com.wealthfront.magellan.NavigationType
import com.wealthfront.magellan.Navigator
import com.wealthfront.magellan.Screen
import com.wealthfront.magellan.ScreenLifecycleListener
import com.wealthfront.magellan.support.SingleActivity
import com.wealthfront.magellan.transitions.DefaultTransition
import com.wealthfront.magellan.transitions.NoAnimationTransition
import de.fayard.android.kiss.notes.NotesScreen
import timber.log.Timber.i

/** A [MagellanScreen] is either associated to a [MainActivity] or no activity if in the background **/
fun MagellanScreen<*>.mainActivity(): MainActivity? =
    (this.activity as? MainActivity)

class MainActivity : SingleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_activity)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                getNavigator().goBackToRoot(NavigationType.GO)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun createNavigator(): Navigator {
        val transition = if (isRunningTest) NoAnimationTransition() else DefaultTransition()
        val navigator = Navigator.withRoot(NotesScreen())
            .transition(transition)
            .loggingEnabled(true)
            .build()
        navigator.addLifecycleListener(LifeCycle)
        return navigator
    }

    val LifeCycle = object : ScreenLifecycleListener {
        override fun onShow(screen: Screen<*>) {
            val title = screen.getTitle(applicationContext)
            i("onShow(screen = '$title')")

            with(supportActionBar!!) {
                val firstScreen = screen is NotesScreen
                setDisplayUseLogoEnabled(firstScreen)
                setDisplayHomeAsUpEnabled(!firstScreen)
            }
        }

        override fun onHide(screen: Screen<*>) {
            val title = screen.getTitle(applicationContext)
            i("onHide(screen = '$title')")
        }
    }
}


