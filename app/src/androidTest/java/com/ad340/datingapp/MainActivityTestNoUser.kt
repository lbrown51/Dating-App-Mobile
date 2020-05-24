package com.ad340.datingapp

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class MainActivityTestNoUser {
    @get:Rule
    val activityRule
            = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var firebaseAuth: FirebaseAuth

    @Before
    fun init() {
        firebaseAuth = mock(FirebaseAuth::class.java)

        FirebaseAuthGetter.firebaseAuth = firebaseAuth
        `when`(firebaseAuth.currentUser).thenReturn(null)

        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    @Test
    fun goesToSignUpIfNoCurrentUser() {
        onView(withId(R.id.sign_in_btn))
            .check(matches(isDisplayed()))
    }

//    @Test
//    fun canClickSignUp() {
//        onView(withId(R.id.sign_in_btn))
//            .perform(click())
//        pressBack()
//        onView(withId(R.id.sign_in_title))
//            .check(matches(isDisplayed()))
//    }
}