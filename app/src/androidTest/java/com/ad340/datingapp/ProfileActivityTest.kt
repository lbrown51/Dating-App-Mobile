package com.ad340.datingapp

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<ProfileActivity>()

    @Test
    fun componentIsDisplayed() {
        onView(withId(R.id.signup_thanks_text))
            .check(matches(isDisplayed()))
    }
}