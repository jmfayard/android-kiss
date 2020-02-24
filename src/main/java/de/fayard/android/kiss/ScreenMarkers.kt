package de.fayard.android.kiss

import android.content.Context

/**
 * An screen marker extends this interface to add a common feature to any [MagellanScreen] tagged with this interface.
 * See [BackGoesToRoot], [HasSnackbar], [UseCoroutines]
 ***/
interface ScreenMarker {
    /** Override if you need to put things in [MagellanScreen.onShow] **/
    fun setup(context: Context) : Unit {}

    /** Override if you need to put things in [MagellanScreen.onHide] **/
    fun cleanup(context: Context) : Unit {}

    /** Cast the screen marker as a screen.
     ** A screenmarker is required to be a [MagellanScreen]. **/
    @Suppress("UNCHECKED_CAST")
    fun <Display : IDisplay> screen() : MagellanScreen<Display> {
        val screen = this as? MagellanScreen<*>
        if (screen == null) error("Object $this is not a MagellanScreen")
        else return screen as MagellanScreen<Display>
    }

    /** Work around the limitation that you cannot store data in an interface  **/
    val data : MutableMap<String, Any> get() = screen<IDisplay>().markersData
}

