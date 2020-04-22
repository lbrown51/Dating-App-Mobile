package com.ad340.datingapp


import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun hasTextOnScreen() {
        Espresso.onView(withId(R.id.hello_text))
            .check(ViewAssertions.matches(withText(R.string.hello_world)))
    }
}