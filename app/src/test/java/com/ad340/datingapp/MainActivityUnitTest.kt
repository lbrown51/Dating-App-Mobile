package com.ad340.datingapp

import android.os.Bundle
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import java.time.LocalDate

import org.mockito.Mockito.spy

class MainActivityUnitTest {
    val mainActivity = MainActivity()

    @Test
    fun onCreate() {
    }

    @Test
    fun onRestoreInstanceState() {
        val bundleWithoutKeyDob = Bundle()
        val initialDateOfBirthMap = mutableMapOf(
            Constants.KEY_DAY to 1,
            Constants.KEY_MONTH to 1,
            Constants.KEY_YEAR to 1
        )

        val mockMainActivity = mock(mainActivity::class.java)
        mockMainActivity.onRestoreInstanceState(bundleWithoutKeyDob)

        val currentDateOfBirthMap = mainActivity.getDateOfBirthMap()
        assertEquals("Failure - maps should be equal", initialDateOfBirthMap, currentDateOfBirthMap)
    }

    @Test
    fun onSaveInstanceState() {
    }

    @Test
    fun goToProfileActivity() {
    }

    @Test
    fun showDateOfBirthPickerDialog() {
    }

    @Test
    fun onDateClick() {
    }

    @Test
    fun isEmailInvalid() {
    }

    @Test
    fun getDateOfBirthArr() {
        val dateOfBirthMap: MutableMap<String, Int?> = mutableMapOf(
            Constants.KEY_DAY to null,
            Constants.KEY_MONTH to null,
            Constants.KEY_YEAR to null
        )
        val dateOfBirthArr = mainActivity.getDateOfBirthArr(dateOfBirthMap)
        assertArrayEquals(
            "Failure - arrays are not equal",
            dateOfBirthArr,
            intArrayOf(0, 0, 0))
    }

    @Test
    fun getDateOfBirthLocalDate() {
        val dateOfBirthMap: MutableMap<String, Int?> = mutableMapOf(
            Constants.KEY_DAY to null,
            Constants.KEY_MONTH to null,
            Constants.KEY_YEAR to null
        )
        val dateOfBirthDate = mainActivity.getDateOfBirthLocalDate(dateOfBirthMap)
        assertEquals(
            "Failure - dates are not equal",
            dateOfBirthDate,
            LocalDate.of(0, 1, 1))
    }

    @Test
    fun setDateOfBirthMap() {
        val dateOfBirthArr = intArrayOf(0, 0, 0)
        val shouldBeTrue = mainActivity.setDateOfBirthMap(dateOfBirthArr)
        assertTrue("Failure - should be true", shouldBeTrue)

        val nullDateOfBirthArr = null
        val shouldBeFalse = mainActivity.setDateOfBirthMap(nullDateOfBirthArr)
        assertFalse("Failure - should be false", shouldBeFalse)
    }
}