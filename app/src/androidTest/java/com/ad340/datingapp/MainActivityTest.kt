package com.ad340.datingapp

import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.rule.IntentsTestRule
import org.hamcrest.core.AllOf.allOf


import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun hasTextOnScreen() {
        onView(withId(R.id.hello_text))
            .check(matches(withText(R.string.hello_world)))

        onView(withId(R.id.name_date))
            .check(matches(withText(R.string.name_date_text)))
    }

    @Test
    fun checkHintMatches() {
        onView(withId(R.id.name_edit_text))
            .check(matches(withHint(R.string.enter_name)))

        onView(withId(R.id.email_edit_text))
            .check(matches(withHint(R.string.enter_email)))

        onView(withId(R.id.username_edit_text))
            .check(matches(withHint(R.string.enter_username)))

        onView(withId(R.id.age_edit_text))
            .check(matches(withHint(R.string.enter_age)))
    }

    @Test
    fun canEnterText() {
        onView(withId(R.id.name_edit_text))
            .perform(typeText("Bob Doe"), closeSoftKeyboard())
            .check(matches(withText("Bob Doe")))

        onView(withId(R.id.email_edit_text))
            .perform(typeText("bdoe@gmail.com"), closeSoftKeyboard())
            .check(matches(withText("bdoe@gmail.com")))

        onView(withId(R.id.username_edit_text))
            .perform(typeText("bdoe"), closeSoftKeyboard())
            .check(matches(withText("bdoe")))

        onView(withId(R.id.age_edit_text))
            .perform(typeText("25"), closeSoftKeyboard())
            .check(matches(withText("25")))
    }

    @Test
    fun canEnterDate() {

        fun withDate(year: Int, month: Int, day: Int): Matcher<View> {
            return object: TypeSafeMatcher<View>() {
                override fun describeTo(description: Description?) {
                    description?.appendText("matches date:")
                }

                override fun matchesSafely(item: View): Boolean {
                    println("")
                    return if (item is DatePicker) {
                        val matchesYear = year == (item as DatePicker).year
                        val matchesMonth = month == (item as DatePicker).month + 1
                        val matchesDay = day == (item as DatePicker).dayOfMonth
                        matchesYear && matchesMonth && matchesDay
                    } else return false
                }
            }
        }

        onView(withId(R.id.date_of_birth))
            .perform(PickerActions.setDate(2010, 12, 5))
            .perform(closeSoftKeyboard())
            .check(matches(withDate(2010, 12, 5)))
    }

    @Test
    fun canGoToProfileWithInfo() {
        onView(withId(R.id.username_edit_text))
            .perform(typeText("bdoe"), closeSoftKeyboard())

        onView(withId(R.id.date_of_birth))
            .perform(PickerActions.setDate(2000, 12, 5), closeSoftKeyboard())

        try {
            Intents.init()

            onView(withId(R.id.submit_profile_btn)).perform(scrollTo())
            Thread.sleep(1000)

            onView(withId(R.id.submit_profile_btn)).check(matches(isDisplayingAtLeast(90)))
            onView(withId(R.id.submit_profile_btn)).perform(click())

            intended(allOf(
                hasExtraWithKey(Constants.KEY_NAME),
                hasExtraWithKey(Constants.KEY_EMAIL),
                hasExtraWithKey(Constants.KEY_USERNAME),
                hasExtraWithKey(Constants.KEY_AGE),
                hasExtraWithKey(Constants.KEY_DOB)
            ))
            //intended(hasComponent(ProfileActivity::class.simpleName))
        } finally {
            Intents.release()
        }

    }

//    @Test
//    fun stoppedWhenNotOldEnough() {
//        onView(withId(R.id.username_edit_text))
//            .perform(typeText("bdoe"), closeSoftKeyboard())
//
//        onView(withId(R.id.date_of_birth))
//            .perform(PickerActions.setDate(2019, 12, 5), closeSoftKeyboard())
//
//        onView(withId(R.id.submit_profile_btn)).perform(scrollTo())
//
//        onView(withId(R.id.submit_profile_btn)).check(matches(isDisplayingAtLeast(90)))
//        onView(withId(R.id.submit_profile_btn)).perform(click())
//
//        onView(withId(R.id.signup_problem_text))
//            .check(matches(withText(R.string.not_old_enough)))
//    }
}