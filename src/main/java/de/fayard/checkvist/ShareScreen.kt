package de.fayard.checkvist

import android.content.Context
import com.wealthfront.magellan.HideUpButton
import com.wealthfront.magellan.IDisplay
import com.wealthfront.magellan.MagellanScreen
import com.wealthfront.magellan.ScreenSetups
import com.wealthfront.magellan.UseCoroutines

interface ShareDisplay : IDisplay

fun ScreenSetups.shareDisplay() = object : ShareDisplay {

}

class ShareScreen :
    HideUpButton,
    UseCoroutines,
    MagellanScreen<ShareDisplay>(R.layout.share_screen, R.string.share_title, ScreenSetups::shareDisplay) {

    override fun onShow(context: Context) {
        super.onShow(context)
        R.id.coordinatorLayout
    }

    override fun onHide(context: Context) {
        super.onHide(context)
    }
}
