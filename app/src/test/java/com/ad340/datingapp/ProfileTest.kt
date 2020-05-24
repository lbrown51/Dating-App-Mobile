package com.ad340.datingapp

import android.os.BaseBundle
import android.os.Bundle
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.spy

class ProfileTest {
    private val bundle = Bundle()
    private val profileFragment = Profile(bundle)

    @Test
    fun onCreateView() {
        val bundleIsNull = profileFragment.bundleIsNotNull(null)
        assertFalse("Failure - bundle should be null", bundleIsNull)

        val bundleDoesNotContainKeyName = !profileFragment
            .bundleContainsKey(bundle, Constants.KEY_NAME)
        assertTrue("Failure - bundle should not contain name key", bundleDoesNotContainKeyName)

        val bundleDoesNotContainKeyAge = !profileFragment
            .bundleContainsKey(bundle, Constants.KEY_AGE)
        assertTrue("Failure - bundle should not contain age key", bundleDoesNotContainKeyAge)


        val bundleDoesNotContainKeyOccupation = !profileFragment
            .bundleContainsKey(bundle, Constants.KEY_OCCUPATION)
        assertTrue(
            "Failure - bundle should not contain occupation key",
            bundleDoesNotContainKeyOccupation
        )


        val bundleDoesNotContainKeyDescription = !profileFragment
            .bundleContainsKey(bundle, Constants.KEY_DESCRIPTION)
        assertTrue(
            "Failure - bundle should not contain description key",
            bundleDoesNotContainKeyDescription
        )
    }
}

//private fun BaseBundle.containsKey() = false
