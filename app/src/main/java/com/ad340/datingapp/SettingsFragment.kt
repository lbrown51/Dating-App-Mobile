package com.ad340.datingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import kotlinx.android.synthetic.main.fragment_settings.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var profileSettings: SettingsEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Get the view model
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        // Observe the user settings
        settingsViewModel.settings.observe(viewLifecycleOwner, Observer { settings ->
            if (settings != null) {
                profileSettings = settings
                view.is_public_switch.isChecked = settings.isPublic
                view.gender_edit_text.setText(settings.gender)
            } else {
                val auth = FirebaseAuthGetter.firebaseAuth!!
                val currentUser = auth.currentUser
                val newSettings = SettingsEntity(
                    uid = currentUser!!.uid,
                    gender = "None",
                    isPublic = false,
                    minAge = 18,
                    maxAge = 30,
                    maximumSearchDistance = 10
                )
                settingsViewModel.insert(newSettings)
                profileSettings = newSettings
            }
        })

        view.is_public_switch.setOnClickListener {
            profileSettings = updateSettings(view, profileSettings)
            settingsViewModel.update(profileSettings)
        }

        view.gender_edit_text.setOnFocusChangeListener { _: View, _: Boolean ->
            profileSettings = updateSettings(view, profileSettings)
            settingsViewModel.update(profileSettings)
        }

        return view
    }

    private fun updateSettings(view: View, profileSettings: SettingsEntity): SettingsEntity {
        profileSettings.isPublic = view.is_public_switch.isChecked
        profileSettings.gender = view.gender_edit_text.text.toString()
        return profileSettings
    }
}
