package de.fayard.android.kiss

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.LayoutRes
import com.wealthfront.magellan.BaseScreenView

typealias ScreenSetups = MagellanView<*>

/** The ScreenView of all yours [MagellanScreen] **/
@SuppressLint("ViewConstructor")
open class MagellanView<Display : IDisplay>(
    context: Context, @LayoutRes val layout: Int,
    setup: MagellanView<Display>.() -> Display
) : BaseScreenView<MagellanScreen<Display>>(context) {
    val display: Display

    init {
        inflate(context, layout, this)
        display = setup()
    }
}