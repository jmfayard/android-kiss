package com.wealthfront.magellan

/** A [ScreenMarker] to tell [MagellanScreen.handleBack] to handle the back button to navigate to the top **/
interface BackGoesToRoot : ScreenMarker

/** A [ScreenMarker] to tell [MagellanScreen.handleBack] to hide the Up button **/
interface HideUpButton