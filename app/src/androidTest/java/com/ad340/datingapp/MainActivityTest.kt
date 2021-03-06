package com.ad340.datingapp


import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.view.View
import android.widget.DatePicker
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.time.LocalDate


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule
            = ActivityTestRule(MainActivity::class.java, true, false)

    private val testName = "Fae Bington"
    private val testEmail = "fbington@gmail.com"
    private val testAge = "25"
    private val testOccupation = "Cat King"
    private val testDescription = "Cat king of the jungle, super magic cat that conquered " +
            "the west indies, slayed the generals of the world, and never worried about anything. "

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    @Before
    fun init() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        firebaseUser = mock(FirebaseUser::class.java)

        FirebaseAuthGetter.firebaseAuth = firebaseAuth
        `when`(firebaseAuth.currentUser).thenReturn(firebaseUser)

        val intent = Intent()
        activityRule.launchActivity(intent)
    }

    @Test
    fun hasTextOnScreen() {
        onView(withId(R.id.hello_text))
            .check(matches(withText(R.string.enter_profile_info)))

        onView(withId(R.id.name_date))
            .check(matches(withText(R.string.name_date_text)))
    }

    @Test
    fun checkHintMatches() {
        onView(withId(R.id.name_edit_text))
            .check(matches(withHint(R.string.enter_name)))

        onView(withId(R.id.email_edit_text))
            .check(matches(withHint(R.string.enter_email)))

        onView(withId(R.id.occupation_edit_text))
            .check(matches(withHint(R.string.enter_occupation)))

        onView(withId(R.id.age_edit_text))
            .check(matches(withHint(R.string.enter_age)))

        onView(withId(R.id.description_text))
            .check(matches(withHint(R.string.describe_yourself)))
    }

    @Test
    fun canEnterText() {
        onView(withId(R.id.name_edit_text))
            .perform(typeText(testName), closeSoftKeyboard())
            .check(matches(withText(testName)))

        onView(withId(R.id.email_edit_text))
            .perform(typeText(testEmail), closeSoftKeyboard())
            .check(matches(withText(testEmail)))

        onView(withId(R.id.occupation_edit_text))
            .perform(typeText(testOccupation), closeSoftKeyboard())
            .check(matches(withText(testOccupation)))

        onView(withId(R.id.description_text))
            .perform(typeText(testDescription), closeSoftKeyboard())

        Thread.sleep(1000)
        onView(withId(R.id.age_edit_text))
            .perform(typeText(testAge), closeSoftKeyboard())
            .check(matches(withText(testAge)))
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
        Thread.sleep(2000)
        onView(withId(R.id.date_of_birth_picker))
            .perform(PickerActions.setDate(2000, 12, 5))
            .perform( closeSoftKeyboard())
        Thread.sleep(2000)
        onView(withId(R.id.confirm_date_of_birth_btn))
            .perform(click())

        val dateOfBirthMap = activityRule.activity.getDateOfBirthMap()

        assert(dateOfBirthMap["year"] == 2000)
        assert(dateOfBirthMap["month"] == 12)
        assert(dateOfBirthMap["day"] == 5)

        val dateOfBirthArr = activityRule.activity.getDateOfBirthArr(dateOfBirthMap)
        assert(dateOfBirthArr[0] == 2000)
        assert(dateOfBirthArr[1] == 12)
        assert(dateOfBirthArr[2] == 5)

        val dateOfBirthLocalDate = activityRule.activity.getDateOfBirthLocalDate(dateOfBirthMap)
        val testLocalDate = LocalDate.of(2000, 12, 5)
        assert(dateOfBirthLocalDate.equals(testLocalDate))
    }

    @Test
    fun validateNullDateOfBirthFuncs() {
        val dateOfBirthMap = activityRule.activity.getDateOfBirthMap()

        assert(dateOfBirthMap["year"] == 1)
        assert(dateOfBirthMap["month"] == 1)
        assert(dateOfBirthMap["day"] == 1)

        val dateOfBirthArr = activityRule.activity.getDateOfBirthArr(dateOfBirthMap)
        assert(dateOfBirthArr[0] == 1)
        assert(dateOfBirthArr[1] == 1)
        assert(dateOfBirthArr[2] == 1)

        val dateOfBirthLocalDate = activityRule.activity.getDateOfBirthLocalDate(dateOfBirthMap)
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
            .perform(typeText(testName), closeSoftKeyboard())
            .check(matches(withText(testName)))
        Thread.sleep(1000)
        onView(withId(R.id.email_edit_text))
            .perform(typeText(testEmail), closeSoftKeyboard())
            .check(matches(withText(testEmail)))
        onView(withId(R.id.occupation_edit_text))
            .perform(typeText(testOccupation), closeSoftKeyboard())
            .check(matches(withText(testOccupation)))
        onView(withId(R.id.description_text))
            .perform(typeText(testDescription), closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withId(R.id.age_edit_text))
            .perform(typeText(testAge), closeSoftKeyboard())
            .check(matches(withText(testAge)))


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

        onView(withId(R.id.profile_name_age_text))
            .check(matches(isDisplayed()))
    }

    @Test
    fun canGoToProfileWithInfo() {
        onView(withId(R.id.name_edit_text))
            .perform(typeText(testName), closeSoftKeyboard())
        onView(withId(R.id.email_edit_text))
            .perform(typeText(testEmail), closeSoftKeyboard())
        onView(withId(R.id.occupation_edit_text))
            .perform(typeText(testOccupation), closeSoftKeyboard())
        onView(withId(R.id.description_text))
            .perform(typeText(testDescription), closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withId(R.id.age_edit_text))
            .perform(typeText(testAge), closeSoftKeyboard())

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
                hasExtraWithKey(Constants.KEY_OCCUPATION),
                hasExtraWithKey(Constants.KEY_AGE),
                hasExtraWithKey(Constants.KEY_DESCRIPTION),
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
            .perform(typeText(testName), closeSoftKeyboard())
        onView(withId(R.id.email_edit_text))
            .perform(typeText(testEmail), closeSoftKeyboard())
        onView(withId(R.id.occupation_edit_text))
            .perform(typeText(testOccupation), closeSoftKeyboard())
        onView(withId(R.id.description_text))
            .perform(typeText(testDescription), closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withId(R.id.age_edit_text))
            .perform(typeText(testAge), closeSoftKeyboard())


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
            .perform(typeText(testName), closeSoftKeyboard())

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
        // Erase text from email edit text at this point
        onView(withId(R.id.email_edit_text))
            .perform(typeText(testEmail), closeSoftKeyboard())

        // Check no username signup problem
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.no_occupation_entered)))
        onView(withId(R.id.occupation_edit_text))
            .perform(typeText(testOccupation), closeSoftKeyboard())

        // Check no age signup problem
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.no_age_entered)))
        onView(withId(R.id.age_edit_text))
            .perform(typeText(testAge), closeSoftKeyboard())

        // Check no description signup problem
        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
            .perform(click())
        Thread.sleep(1000)
        onView(withId(R.id.signup_problem_text))
            .check(matches(withText(R.string.no_description_entered)))
        onView(withId(R.id.description_text))
            .perform(typeText(testDescription), closeSoftKeyboard())

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
            .perform(typeText(testName), closeSoftKeyboard())
        onView(withId(R.id.email_edit_text))
            .perform(typeText(testEmail), closeSoftKeyboard())
        onView(withId(R.id.occupation_edit_text))
            .perform(typeText(testOccupation), closeSoftKeyboard())
        onView(withId(R.id.description_text))
            .perform(typeText(testDescription), closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withId(R.id.age_edit_text))
            .perform(typeText(testAge), closeSoftKeyboard())

        onView(withId(R.id.date_of_birth_btn))
            .perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.date_of_birth_picker))
            .perform(PickerActions.setDate(2000, 12, 5))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.confirm_date_of_birth_btn))
            .perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.submit_profile_btn))
            .perform(scrollTo())
            .check(matches(isDisplayingAtLeast(90)))
        onView(withId(R.id.submit_profile_btn)).perform(click())

        Thread.sleep(1000)
        Espresso.pressBack()
        Thread.sleep(1000)

        onView(withId(R.id.hello_text))
            .check(matches(withText(R.string.enter_profile_info)))

        onView(withId(R.id.name_edit_text))
            .check(matches(withHint(R.string.enter_name)))

        onView(withId(R.id.email_edit_text))
            .check(matches(withHint(R.string.enter_email)))

        onView(withId(R.id.occupation_edit_text))
            .check(matches(withHint(R.string.enter_occupation)))

        onView(withId(R.id.age_edit_text))
            .check(matches(withHint(R.string.enter_age)))

        onView(withId(R.id.date_of_birth_btn))
            .check(matches(withText(R.string.select_dob_text)))
    }

    @Test
    fun profileCanRenderInfo() {
        onView(withId(R.id.name_edit_text))
            .perform(typeText(testName), closeSoftKeyboard())
        onView(withId(R.id.email_edit_text))
            .perform(typeText(testEmail), closeSoftKeyboard())
        onView(withId(R.id.occupation_edit_text))
            .perform(typeText(testOccupation), closeSoftKeyboard())
        onView(withId(R.id.description_text))
            .perform(typeText(testDescription), closeSoftKeyboard())
        Thread.sleep(1000)
        onView(withId(R.id.age_edit_text))
            .perform(typeText(testAge), closeSoftKeyboard())

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

            Thread.sleep(3000)

            val nameAgeStr = StringBuilder()
            nameAgeStr.append(testName)
            nameAgeStr.append(", ")
            nameAgeStr.append(testAge)
            onView(withId(R.id.profile_name_age_text))
                .check(matches(withText(nameAgeStr.toString())))

            onView(withId(R.id.profile_occupation_text))
                .check(matches(withText(testOccupation)))
        } finally {
            Intents.release()
        }
    }

}