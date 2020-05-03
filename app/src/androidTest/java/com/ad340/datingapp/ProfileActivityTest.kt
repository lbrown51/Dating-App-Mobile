package com.ad340.datingapp

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.rule.ActivityTestRule

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(ProfileActivity::class.java)

    @Test
    fun componentIsDisplayed() {
        onView(withId(R.id.profile_picture))
            .check(matches(isDisplayed()))
    }

    @Test
    fun callOnDateClick() {
        activityRule.activity.onDateClick()
    }
}