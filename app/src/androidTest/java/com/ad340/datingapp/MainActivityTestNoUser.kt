package com.ad340.datingapp

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class MainActivityTestNoUser {
    @get:Rule
    val activityRule
            = ActivityTestRule(MainActivity::class.java, true, false)

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    @Before
    fun init() {
        firebaseAuth = Mockito.mock(FirebaseAuth::class.java)

        FirebaseAuthGetter.firebaseAuth = firebaseAuth
        Mockito.`when`(firebaseAuth.currentUser).thenReturn(null)

        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    @Test
    fun goesToSignUpIfNoCurrentUser() {
        Espresso.onView(ViewMatchers.withId(R.id.sign_in_btn))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}