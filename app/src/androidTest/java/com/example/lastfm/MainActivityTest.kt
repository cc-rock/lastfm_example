package com.example.lastfm

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.util.HumanReadables
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.util.TreeIterables
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun launchActivity_enterQuery_checkThatResultsAreDisplayed() {
        onView(withId(R.id.search_field))
            .perform(typeText("queen"), closeSoftKeyboard())
        onView(isRoot()).perform(waitText("Queens of the Stone Age", 5000))
    }

    @Test
    fun click_on_result_and_open_detail() {
        onView(withId(R.id.search_field))
            .perform(typeText("Queen"), closeSoftKeyboard())
        onView(isRoot()).perform(waitText("Queens of the Stone Age", 5000))
        onView(withText("Queens of the Stone Age")).perform(click())
        onView(isRoot()).perform(waitText("Queens of the Stone Age is a rock band from Palm Desert", 5000))
    }

    /**
     * Perform action of waiting for a view with the given text.
     * @param viewId The id of the view to wait for.
     * @param millis The timeout of until when to wait for.
     */
    fun waitText(text: String, millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with text <$text> during $millis millis."
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher = withSubstring(text)

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)

                // timeout happens
                throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
            }
        }
    }
}
