package com.ad340.datingapp

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_matches.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [Matches.newInstance] factory method to
 * create an instance of this fragment.
 */
class Matches : Fragment() {
    private lateinit var locationManager: LocationManager
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var profileSettings: SettingsEntity
    private lateinit var matchList: List<MatchItem>

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get view
        val view = inflater.inflate(R.layout.fragment_matches, container, false)

        val firebaseMatchViewModel = ViewModelProvider(this)[FirebaseMatchViewModel::class.java]
        val adapter = MatchCardAdapter(context!!, firebaseMatchViewModel)
        setupSettings(adapter)

        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Set up the RecyclerView
        view.matches_recycler_view.setHasFixedSize(true)
        view.matches_recycler_view.layoutManager = LinearLayoutManager(context)

        view.matches_recycler_view.adapter = adapter
        val largePadding = resources.getDimensionPixelSize(R.dimen.item_spacing)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.small_item_spacing)
        view.matches_recycler_view.addItemDecoration(MatchesItemDecoration(largePadding, smallPadding))

        val hasFinePermission = ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarsePermission = ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        firebaseMatchViewModel.getMatches().observe(viewLifecycleOwner, Observer { matchList ->
            this.matchList = matchList
            if (checkLocation() && (hasFinePermission || hasCoarsePermission)) {
                val criteria = Criteria()
                val locationProvider = locationManager.getBestProvider(criteria, false)
                val location = locationManager.getLastKnownLocation(locationProvider)

                val metersToMilesRatio = 1609.34

                matchList
                    .filter {
                        val matchLocation = Location("")
                        matchLocation.latitude = it.lat.toDouble()
                        matchLocation.longitude = it.longitude.toDouble()
                        val distanceFromMatch = location.distanceTo(matchLocation)

                        distanceFromMatch < profileSettings.maximumSearchDistance * metersToMilesRatio
                    }
                    .let {
                        adapter.setMatchList(it)
                    }
            } else {
                matchList?.let { adapter.setMatchList(it) }
            }

        })

        // Inflate the layout for this fragment
        return view
    }

    private fun setupSettings(adapter: MatchCardAdapter) {
        // Get view model
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        // Observe the user settings
        settingsViewModel.settings.observe(viewLifecycleOwner, Observer { settings ->
            if (settings != null) {
                profileSettings = settings

                if (this::matchList.isInitialized) {
                    val hasFinePermission = ActivityCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                    val hasCoarsePermission = ActivityCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED

                    if (checkLocation() && (hasFinePermission || hasCoarsePermission)) {
                        val criteria = Criteria()
                        val locationProvider = locationManager.getBestProvider(criteria, false)
                        val location = locationManager.getLastKnownLocation(locationProvider)

                        val metersToMilesRatio = 1609.34

                        matchList
                            .filter {
                                val matchLocation = Location("")
                                matchLocation.latitude = it.lat.toDouble()
                                matchLocation.longitude = it.longitude.toDouble()
                                val distanceFromMatch = location.distanceTo(matchLocation)

                                distanceFromMatch < profileSettings.maximumSearchDistance * metersToMilesRatio
                            }
                            .let {
                                adapter.setMatchList(it)
                            }
                    }
                }
            }
        })
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled() && !IsTest.isTest) {
            showAlert()
        }
        return isLocationEnabled()
    }

    private fun isLocationEnabled(): Boolean {
        val hasFinePermission = ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarsePermission = ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val providerEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return providerEnabled && (hasFinePermission || hasCoarsePermission)
    }

    private fun showAlert() {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this.activity)
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to \\'Off\\'. Please Enable Location to use this app")
            .setPositiveButton("Location Settings") { _, _ ->
                ActivityCompat.requestPermissions(
                    this.activity!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            .setNegativeButton("Cancel") { _, _ -> }
        dialog.create().show()
    }
}
