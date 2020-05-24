package com.ad340.datingapp

import android.os.Bundle
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ProfileFragmentTest {
    private val bundle = mock(Bundle::class.java)
    private val profileFragment = Profile(bundle)

    @Test
    fun onCreateView() {
        `when`(bundle.containsKey("key")).thenReturn(false)

        val getNameAgeErr = profileFragment.getNameAgeStr(bundle)
        assertEquals(
            "Failure - return message should be an error",
            getNameAgeErr,
            "Error, could not get name age string"
        )

        val getOccupationErr = profileFragment.getOccupationStr(bundle)
        assertEquals(
            "Failure - return message should be an error",
            getOccupationErr,
            "Error, could not get occupation string"
        )

        val getDescriptionErr = profileFragment.getDescriptionStr(bundle)
        assertEquals(
            "Failure - return message should be an error",
            getDescriptionErr,
            "Error, could not get description string"
        )
    }
}

//private fun BaseBundle.containsKey() = false
