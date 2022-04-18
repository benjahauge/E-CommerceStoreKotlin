package com.example.mandatoryassignment

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mandatoryassignment.models.Item
import com.example.mandatoryassignment.models.ItemViewModel
import com.example.mandatoryassignment.models.MyAdapter

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val itemViewModel: ItemViewModel = ItemViewModel()

    private lateinit var item: Item


    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mandatoryassignment", appContext.packageName)

//        //UI Testing for Descending sort
        onView(withText("Sort By Price Desc")).check(matches(isDisplayed()))
        onView(withId(R.id.button_sortPriceDesc)).perform(click())
        onView(withText(
            itemViewModel.itemsLiveDataMutable.value?.sortedByDescending { it.price }?.get(0).toString())).check(matches(isDisplayed()))

        pause(2000)

        //UI Testing for Ascending sort
        onView(withText("Sort By Price")).check(matches(isDisplayed()))
        onView(withId(R.id.button_sortPriceAsc)).perform(click())
        onView(withText(
            itemViewModel.itemsLiveDataMutable.value?.sortedBy { it.price }?.get(0).toString())).check(matches(isDisplayed()))
        pause(2000)


        //Signout and log-in
        onView(withText("Welcome benja@secret123.dk"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.action_signout)).perform(click())
        pause(2000)

        onView(withId(R.id.fab)).perform(click())
        onView(withText("Back"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.button_back)).perform(click())

        onView(withText("Welcome null"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.button_login)).perform(click())

        onView(withText("Authentication")).check(matches(isDisplayed()))
        onView(withId(R.id.emailInputField))
            .perform(clearText())
            .perform(typeText("benja@secret123.dk"))
        onView(withId(R.id.passwordInputField))
            .perform(clearText())
            .perform(typeText("secret123"))
            .perform(closeSoftKeyboard())
        onView(withId(R.id.sign_in)).perform(click())
        pause(2000)
        onView(withId(R.id.textview_user)).check(matches(withText(("Welcome benja@secret123.dk"))))


        //ADD and object
        onView(withId(R.id.fab)).perform(click())
        pause(2000)
        onView(withId(R.id.title)).perform(clearText()).perform(typeText("Espresso 101"))

        onView(withId(R.id.description))
            .perform(clearText())
            .perform(typeText("Book about Espresso in Android"))
        onView(withId(R.id.price))
            .perform(clearText())
            .perform(typeText("109"))
        pause(2000)
        onView(withText("Add"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.button_add)).perform(click())
        pause(2000)
        onView(withText(
            itemViewModel.itemsLiveDataMutable.value?.get(0).toString())).check(matches(isDisplayed()))
        pause(2000)

        onView(withId(R.id.swiperefresh)).perform(swipeDown())
        pause(2000)

        onView(withText(
            itemViewModel.itemsLiveDataMutable.value?.get(0).toString())).perform(click())
        pause(2000)

        onView(withText("Back"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.button_back)).perform(click())
        pause(2000)

    }


    private fun pause(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

}