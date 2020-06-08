package com.ad340.datingapp

import android.app.Activity
import android.content.Context
import android.location.LocationManager
import java.io.Serializable

object LocationManagerGetter: Serializable {
    var locationManager: LocationManager? = null

    fun getLocationManager(activity: Activity): LocationManager? {
        return if (locationManager != null) locationManager
        else {
            activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }
}