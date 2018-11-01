package com.wealthfront.magellan.kotlinsample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.wealthfront.magellan.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@SuppressLint("ViewConstructor")
open class MagellanView<Display: IDisplay>(context: Context, @LayoutRes val layout: Int, setup: MagellanView<Display>.() -> Display)
    : BaseScreenView<MagellanScreen<Display>>(context) {
    val display: Display

    init {
        inflate(context, layout, this)
        display = setup()
    }
}

/** Base class for all our screens. Used a [MagellanView] as its view. **/
abstract class MagellanScreen<Display : IDisplay>(
        @LayoutRes val screenLayout: Int,
        @StringRes val screenTitle: Int,
        val screenSetup: MagellanView<Display>.() -> Display
) : Screen<MagellanView<Display>>(), CoroutineScope {


    @CallSuper
    override fun onShow(context: Context) {
        screenJob = Job()
    }

    @CallSuper
    override fun onHide(context: Context?) {
        screenJob.cancel()
    }

    private lateinit var screenJob: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + screenJob


    val appCtx: Context get() = App.instance.applicationContext

    val display: Display? get() = testDisplay ?: view?.display

    private var testDisplay: Display? = null

    final override fun getTitle(context: Context): String {
        return context.getString(screenTitle)
    }

    override fun createView(context: Context) =
            MagellanView(context, screenLayout, screenSetup)


    /** i18n(R.String.TAG_LOST) instead of cumbersome and unsafe activity.getString(R.String.blahblah) **/
    fun i18n(@StringRes resId: Int, vararg formatArgs: String): String =
            appCtx.getString(resId, *formatArgs)

    @ColorInt
    fun color(@ColorRes colorId: Int): Int =
            ContextCompat.getColor(appCtx, colorId)


    /** A scren is either associated to a [MainActivity] or no activity if in the background **/
    fun mainActivity(): MainActivity? =
            (this.activity as? MainActivity)

    fun safeShowDialog(dialog: DialogCreator) {
        this.dialog?.run {
            setOnDismissListener(null)
            dismiss()
        }
        if (activity != null) {
            showDialog(dialog)
        }
    }

    /*** Used in unit tests. Calls onShow(). [display] should be a data class implementing Display. [navigator] and [activity] are mocked objects ***/
    @VisibleForTesting
    fun setupForTests(display: Display, navigator: Navigator, activity: Activity): Display {
        this.navigator = navigator
        this.activity = activity
        this.testDisplay = display
        onShow(activity)
        return display
    }

    fun hideSoftKeyboard(editText: HasId) = hideSoftKeyboard(editText.id)

    fun hideSoftKeyboard(@IdRes editText: Int) {
        val widget = view?.findViewById<EditText>(editText)
        if (widget == null) {
            Timber.w("hideSoftKeyboard() failed, could not find EditText(id=0x${editText.toString(16)})")
        } else {
            val context = view?.context ?: return
            if (view != null && view.requestFocus()) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.rootView.windowToken, 0) // InputMethodManager.SHOW_IMPLICIT
            }

        }
    }

    override fun handleBack(): Boolean {
        if (this is BackGoesToRoot) {
            navigator.goBackToRoot(NavigationType.GO)
            return true
        }
        return super.handleBack()
    }

    public override fun onUpdateMenu(menu: Menu) {

    }


}
