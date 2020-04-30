package com.ad340.datingapp

import android.content.Context
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.DatePicker
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.rule.ActivityTestRule
import org.hamcrest.core.AllOf.allOf


import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

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

        Thread.sleep(1000)
        onView(withId(R.id.age_edit_text))
            .perform(typeText("25"), closeSoftKeyboard())
            .check(matches(withText("25")))
    }

    @Test
    fun canOpenDateOfBirthFragment() {
        onView(withId(R.id.date_of_birth_btn))
            .perform(click())

        onView(withId(R.id.date_of_birth_fragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canEnterDateAndConfirmDateOfBirth() {
        onView(withId(R.id.date_of_birth_btn))
            .perform(click())

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

        onView(withId(R.id.date_of_birth_picker))
            .perform(PickerActions.setDate(2010, 12, 5))
            .perform(closeSoftKeyboard())
            .check(matches(withDate(2010, 12, 5)))

        onView(withId(R.id.confirm_date_of_birth_btn))
            .perform(click())
    }

    @Test
    fun validateDateOfBirthFuncs() {
        onView(withId(R.id.date_of_birth_btn))
            .perform(click())
        onView(withId(R.id.date_of_birth_picker))
            .perform(PickerActions.setDate(2000, 12, 5))
            .perform( closeSoftKeyboard())
        onView(withId(R.id.confirm_date_of_birth_btn))
            .perform(click())

        val dateOfBirthMap = activityRule.activity.getDateOfBirthMap()

        assert(dateOfBirthMap["year"] == 2000)
        assert(dateOfBirthMap["month"] == 12)
        assert(dateOfBirthMap["day"] == 5)

        val dateOfBirthArr = activityRule.activity.getDateOfBirthArr()
        assert(dateOfBirthArr[0] == 2000)
        assert(dateOfBirthArr[1] == 12)
        assert(dateOfBirthArr[2] == 5)

        val dateOfBirthLocalDate = activityRule.activity.getDateOfBirthLocalDate()
        val testLocalDate = LocalDate.of(2000, 12, 5)
        assert(dateOfBirthLocalDate.equals(testLocalDate))
    }

    @Test
    fun validateNullDateOfBirthFuncs() {
        val dateOfBirthMap = activityRule.activity.getDateOfBirthMap()

        assert(dateOfBirthMap["year"] == 1)
        assert(dateOfBirthMap["month"] == 1)
        assert(dateOfBirthMap["day"] == 1)

        val dateOfBirthArr = activityRule.activity.getDateOfBirthArr()
        assert(dateOfBirthArr[0] == 1)
        assert(dateOfBirthArr[1] == 1)
        assert(dateOfBirthArr[2] == 1)

        val dateOfBirthLocalDate = activityRule.activity.getDateOfBirthLocalDate()
        val testLocalDate = LocalDate.of(1, 1, 1)
        assert(dateOfBirthLocalDate.equals(testLocalDate))
    }

    @Test
    fun canRotateScreen() {
        val activity = activityRule.activity
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    @Test
    fun canResistDataLossOnOrientationChange() {
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


        onView(withId(R.id.date_of_birth_btn))
            .perform(click())
        Thread.sleep(2000)

        onView(withId(R.id.date_of_birth_picker))
            .perform(PickerActions.setDate(2000, 12, 5))
            .perform( closeSoftKeyboard())
        Thread.sleep(2000)

        onView(withId(R.id.confirm_date_of_birth_btn))
            .perform(click())
        Thread.sleep(2000)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val activity = activityRule.activity
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        Thread.sleep(2000)

        onView(withId(R.id.submit_profile_btn))
            .perform((scrollTo()))
            .perform(click())

        onView(withId(R.id.signup_thanks_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canGoToProfileWithInfo() {
        onView(withId(R.id.name_edit_text))
            .perform(typeText("Bob Doe"), closeSoftKeyboard())
        onView(withId(R.id.email_edit_text))
            .perform(typeText("bdoe@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.username_edit_text))
            .perform(typeText("bdoe"), closeSoftKeyboard())
        onView(withId(R.id.age_edit_text))
            .perform(typeText("25"), closeSoftKeyboard())

        onView(withId(R.id.date_of_birth_btn))
            .perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.date_of_birth_picker))
            .perform(PickerActions.setDate(2000, 12, 5))
            .perform( closeSoftKeyboard())

        onView(withId(R.id.confirm_date_of_birth_btn))
            .perform(click())

        Thread.sleep(2000)

        try {
            Intents.init()

            onView(withId(R.id.submit_profile_btn)).perform(scrollTo())
            Thread.sleep(1000)

            onView(withId(R.id.submit_profile_btn)).check(matches(isDisplayingAtLeast(90)))
            onView(withId(R.id.submit_profile_btn)).perform(click())

            val dateOfBirthArr = intArrayOf(2000, 12, 5)

            intended(allOf(
                hasExtraWithKey(Constants.KEY_NAME),
                hasExtraWithKey(Constants.KEY_EMAIL),
                hasExtraWithKey(Constants.KEY_USERNAME),
                hasExtraWithKey(Constants.KEY_AGE),
                hasExtraWithKey(Constants.KEY_DOB),
                hasExtra(Constants.KEY_DOB, dateOfBirthArr)
            ))
            //intended(hasComponent(ProfileActivity::class.simpleName))
        } finally {
            Intents.release()
        }

    }

    @Test
    fun stoppedWhenNotOldEnough() {
        onView(withId(R.id.name_edit_text))
            .perform(typeText("Bob Doe"), closeSoftKeyboard())
        onView(withId(R.id.email_edit_text))
            .perform(typeText("bdoe@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.username_edit_text))
            .perform(typeText("bdoe"), closeSoftKeyboard())
        onView(withId(R.id.age_edit_text))
            .perform(typeText("25"), closeSoftKeyboard())


        onView(withId(R.id.date_of_birth_btn))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.date_of_birth_picker))
            .perform(PickerActions.setDate(2019, 12, 5))
            .perform( closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withId(R.id.confirm_date_of_birth_btn))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.not_old_enough)))
    }

    @Test
    fun stoppedInputProblem() {
        // Check no name signup problem
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.no_name_entered)))
        onView(withId(R.id.name_edit_text))
            .perform(typeText("Bob Doe"), closeSoftKeyboard())

        // Check no email signup problem
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.no_email_entered)))
        onView(withId(R.id.email_edit_text))
            .perform(typeText("testEmail"), closeSoftKeyboard())

        // Check wrong email format signup problem
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.email_not_valid)))
        onView(withId(R.id.email_edit_text))
            .perform(typeText("bdoe@gmail.com"), closeSoftKeyboard())

        // Check no username signup problem
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.no_username_entered)))
        onView(withId(R.id.username_edit_text))
            .perform(typeText("bdoe"), closeSoftKeyboard())

        // Check no age signup problem
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.no_age_entered)))
        onView(withId(R.id.age_edit_text))
            .perform(typeText("25"), closeSoftKeyboard())

        // Check no date of birth signup problem
//        onView(withId(R.id.submit_profile_btn))
//            .perform(scrollTo())
//            .check(matches(isDisplayingAtLeast(90)))
//            .perform(click())
//        Thread.sleep(1000)
//        onView(withId(R.id.signup_problem_text))
//            .check(matches(withText(R.string.dob_not_selected)))
    }

    @Test
    fun canClearInputsOnResume() {
        onView(withId(R.id.name_edit_text))
            .perform(typeText("Bob Doe"), closeSoftKeyboard())
        onView(withId(R.id.email_edit_text))
            .perform(typeText("bdoe@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.username_edit_text))
            .perform(typeText("bdoe"), closeSoftKeyboard())
        onView(withId(R.id.age_edit_text))
            .perform(typeText("25"), closeSoftKeyboard())

        onView(withId(R.id.date_of_birth_btn))
            .perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.date_of_birth_picker))
            .perform(PickerActions.setDate(2000, 12, 5))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.confirm_date_of_birth_btn))
            .perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.submit_profile_btn)).check(matches(isDisplayingAtLeast(90)))
        onView(withId(R.id.submit_profile_btn)).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
        Thread.sleep(1000)

        onView(withId(R.id.hello_text))
            .check(matches(withText(R.string.hello_world)))

        onView(withId(R.id.name_edit_text))
            .check(matches(withHint(R.string.enter_name)))

        onView(withId(R.id.email_edit_text))
            .check(matches(withHint(R.string.enter_email)))

        onView(withId(R.id.username_edit_text))
            .check(matches(withHint(R.string.enter_username)))

        onView(withId(R.id.age_edit_text))
            .check(matches(withHint(R.string.enter_age)))

        onView(withId(R.id.date_of_birth_btn))
            .check(matches(withText(R.string.select_dob_text)))
    }

}