package com.wealthfront.magellan.kotlinsample

import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    /***
     * Jake Wharton gave an amazing about so-called Testing Robots here
     *   https://news.realm.io/news/kau-jake-wharton-testing-robots/
     *
     * The basic creating is to separate
     * WHAT a test should do as expressed by an high-level declarative DSL below
     * from
     * HOW each action and verification should be implemented (e.g. with Espresso on Android)
     *
     * See a screencast of what this test does here
     * https://slack-files.com/T1J0AQPAR-F5UTUN8KT-b55a1b1c98
     */
    @Test
    fun testingRobot() {

        notes {

        }.addNote {
            hasTitle("")
            hasDescription("")
            enterTitle("Just Going back")
            hasTitle("Just Going back")
        }.goBack {

        }.addNote {
            hasTitle("")
            hasDescription("")
            enterTitle("Testing Robots")
            enterDescription("The Robots Pattern allows to separate WHAT a test should verify from HOW the verifications are implemented")
        }.save {
            selectNote(position = 0)
        }.showNote {
            hasTitle("Testing Robots")
        }.editNote {
            hasTitle("Testing Robots")
            enterDescription("... give it a try!")
        }.save {

        }
    }
}
