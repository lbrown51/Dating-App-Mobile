package com.ad340.datingapp

import android.content.Intent
import android.location.Location
import android.location.LocationManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

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
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var locationManager: LocationManager


    @Before
    fun setup() {
        val intent = Intent()
        intent.putExtra(Constants.KEY_NAME, testName)
        intent.putExtra(Constants.KEY_EMAIL, testEmail)
        intent.putExtra(Constants.KEY_AGE, testAge)
        intent.putExtra(Constants.KEY_OCCUPATION, testOccupation)
        intent.putExtra(Constants.KEY_DESCRIPTION, testDescription)

        val firebaseAuthFromGetter = FirebaseAuthGetter.firebaseAuth
        val newFirebaseAuthInstance = FirebaseAuth.getInstance()

        firebaseAuth = mock(FirebaseAuth::class.java)

        FirebaseAuthGetter.firebaseAuth = firebaseAuth
        `when`(firebaseAuth.currentUser).thenReturn(mock(FirebaseUser::class.java))
        `when`(firebaseAuth.currentUser!!.uid).thenReturn("TestUid")

        IsTest.isTest = true

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

    //@Test
    fun canSwitchBetweenTabs() {
        onView(withText("Matches"))
            .perform(click())
        onView(withId(R.id.matches_recycler_view))
            .check(matches(isDisplayed()))

        onView(withText("Settings"))
            .perform(click())
        onView(withId(R.id.is_public_layout))
            .check(matches(isDisplayed()))

        onView(withText("Profile"))
            .perform(click())
    }

    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher? {
        return RecyclerViewMatcher(recyclerViewId)
    }

    @Test
    fun matchesAreDisplayed() {
        onView(withText("Matches"))
            .perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.matches_recycler_view))
            .check(matches(isDisplayed()))

        onView(withId(R.id.matches_recycler_view))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition<RecyclerView.ViewHolder>
                    (1, click()))
    }

   //@Test
    fun canClickLikeButton() {
        onView(withText("Matches"))
            .perform(click())
        Thread.sleep(3000)
        onView(
            withRecyclerView(R.id.matches_recycler_view)
                ?.atPositionOnView(0, R.id.match_like_button)
        )
            .perform(click())
        Thread.sleep(1000)

        onView(
            withRecyclerView(R.id.matches_recycler_view)
                ?.atPositionOnView(0, R.id.match_like_button)
        )
            .perform(click())
    }

    //@Test
    fun settingsPageIsDisplayedCorrectly() {
        onView(withText("Settings"))
            .perform(click())

        onView(withId(R.id.is_public_switch))
            .check(matches(isDisplayed()))
        onView(withId(R.id.gender_edit_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.minimum_age_edit_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.maximum_age_edit_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.maximum_search_distance_slider))
            .check(matches(isDisplayed()))
    }

    //@Test
    fun canEnterSettings() {
        onView(withText("Settings"))
            .perform(click())

        onView(withId(R.id.is_public_switch))
            .perform(click())

        onView(withId(R.id.gender_edit_text))
            .perform(typeText("Gender Power"))

        onView(withId(R.id.minimum_age_edit_text))
            .perform(typeText("20"))

        onView(withId(R.id.maximum_age_edit_text))
            .perform(typeText("30"))

        onView(withText("Profile"))
            .perform(click())
    }

}