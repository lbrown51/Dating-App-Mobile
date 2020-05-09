package com.ad340.datingapp

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.rule.ActivityTestRule
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest {
    @get:Rule
    val activityRule =
        ActivityTestRule(ProfileActivity::class.java, false, false)

    private val testName = "Fae Bington"
    private val testEmail = "fbington@gmail.com"
    private val testAge = "25"
    private val testOccupation = "Cat King"
    private val testDescription = "Cat king of the jungle, super magic cat that conquered " +
            "the west indies, slayed the generals of the world, and never worried about anything. " +
            "I like long walks on the beach and a roaring camp fire next to which I shall eat " +
            "the remains of my fallen foes. Have no fear, or have fear, because I am here."


    @Before
    fun setup() {
        val intent = Intent()
        intent.putExtra(Constants.KEY_NAME, testName)
        intent.putExtra(Constants.KEY_EMAIL, testEmail)
        intent.putExtra(Constants.KEY_AGE, testAge)
        intent.putExtra(Constants.KEY_OCCUPATION, testOccupation)
        intent.putExtra(Constants.KEY_DESCRIPTION, testDescription)

        activityRule.launchActivity(intent)
    }


    @Test
    fun componentIsDisplayed() {
        onView(withId(R.id.profile_picture))
            .check(matches(isDisplayed()))
    }

    @Test
    fun callOnDateClick() {
        activityRule.activity.onDateClick()
    }

    @Test
    fun infoIsDisplayed() {
        val nameAgeStr = StringBuilder(testName)
        nameAgeStr.append(", ")
        nameAgeStr.append(testAge)
        onView(withId(R.id.profile_name_age_text))
            .check(matches(withText(nameAgeStr.toString())))

        onView(withId(R.id.profile_occupation_text))
            .check(matches(withText(testOccupation)))

        onView(withId(R.id.profile_desc_text))
            .check(matches(withText(testDescription)))
    }

    @Test
    fun canSwitchBetweenTabs() {
        onView(withText("Matches"))
            .perform(click())
        onView(withId(R.id.matches_hello_text))
            .check(matches(withText(R.string.hello_blank_fragment)))

        onView(withText("Settings"))
            .perform(click())
        onView(withId(R.id.settings_hello_text))
            .check(matches(withText(R.string.hello_blank_fragment)))

        onView(withText("Profile"))
            .perform(click())
    }
}