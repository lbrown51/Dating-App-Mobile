package com.ad340.datingapp

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.text.SimpleDateFormat
import java.util.*

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
                view.gender_edit_text.setSelection(settings.gender.length)

                view.minimum_age_edit_text.setText(settings.minAge.toString())
                view.minimum_age_edit_text.setSelection(settings.minAge.toString().length)

                view.maximum_age_edit_text.setText(settings.maxAge.toString())
                view.maximum_age_edit_text.setSelection(settings.maxAge.toString().length)

                view.maximum_search_distance_slider.value = settings.maximumSearchDistance.toFloat()

                if (!settings.dailyMatchReminderTimeStr.equals("0:00 AM")) {
                    view.daily_matches_reminder_btn.text = settings.dailyMatchReminderTimeStr
                }
            } else {
                val auth = FirebaseAuthGetter.firebaseAuth!!
                val currentUser = auth.currentUser
                val newSettings = SettingsEntity(
                    uid = currentUser!!.uid,
                    gender = "None",
                    isPublic = false,
                    minAge = 18,
                    maxAge = 30,
                    maximumSearchDistance = 10,
                    dailyMatchReminderTimeStr = "0:00 AM"
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

        view.maximum_search_distance_slider.setOnFocusChangeListener { _, _ ->
            profileSettings = updateSettings(view, profileSettings)
            settingsViewModel.update(profileSettings)
        }

        view.minimum_age_edit_text.setOnFocusChangeListener { _: View, _: Boolean ->
            profileSettings = updateSettings(view, profileSettings)
            settingsViewModel.update(profileSettings)
        }

        view.maximum_age_edit_text.setOnFocusChangeListener { _: View, _: Boolean ->
            profileSettings = updateSettings(view, profileSettings)
            settingsViewModel.update(profileSettings)
        }

        val calendar = Calendar.getInstance()
        val timeString = StringBuilder()
        val dailyMatchReminderBtn = view.daily_matches_reminder_btn
        val time = onTimeSetListener(calendar, timeString, dailyMatchReminderBtn)


        view.daily_matches_reminder_btn.setOnClickListener {
            TimePickerDialog(
                context, time,
                calendar[Calendar.HOUR_OF_DAY],
                calendar[Calendar.MINUTE],
                false).show()
        }

        view.save_settings_btn.setOnClickListener {
            profileSettings = updateSettings(view, profileSettings)
            settingsViewModel.update(profileSettings)
            Toast.makeText(context, "Changes have been saved", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun onTimeSetListener(
        calendar: Calendar, timeString: java.lang.StringBuilder, v: TextView
    ): OnTimeSetListener? {
        return OnTimeSetListener { _: TimePicker?, hourOfDay: Int, minute: Int ->
            calendar[Calendar.HOUR_OF_DAY] = hourOfDay
            calendar[Calendar.MINUTE] = minute
            val sdf =
                SimpleDateFormat("h:mm a", Locale.US)
            timeString.setLength(0)
            timeString.append(sdf.format(calendar.time))
            v.text = timeString
        }
    }


    private fun updateSettings(view: View, profileSettings: SettingsEntity): SettingsEntity {
        profileSettings.isPublic = view.is_public_switch.isChecked
        profileSettings.gender = view.gender_edit_text.text.toString()
        profileSettings.minAge = view.minimum_age_edit_text.text.toString().toInt()
        profileSettings.maxAge = view.maximum_age_edit_text.text.toString().toInt()
        profileSettings.maximumSearchDistance = view.maximum_search_distance_slider.value.toInt()
        profileSettings.dailyMatchReminderTimeStr = view.daily_matches_reminder_btn.text.toString()
        
        return profileSettings
    }
}
