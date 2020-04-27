package com.ad340.datingapp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker

/**
 * A simple [Fragment] subclass.
 * Use the [DateOfBirthPickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DateOfBirthPickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_date_of_birth_picker,
            container, false)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }

    fun onDateClick(view: View) {

    }
}
