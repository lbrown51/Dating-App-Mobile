package com.ad340.datingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName


    private val dateOfBirthMap = mutableMapOf(
        "day" to 0,
        "month" to 0,
        "year" to 0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        Log.i(TAG, "onRestoreInstanceState()")
        if (savedInstanceState.containsKey(Constants.KEY_DOB)) {
            val dateOfBirthArr= savedInstanceState.getIntArray(Constants.KEY_DOB)
            if (dateOfBirthArr != null) {
                dateOfBirthMap["year"] = dateOfBirthArr[0]
                dateOfBirthMap["month"] = dateOfBirthArr[1]
                dateOfBirthMap["day"] = dateOfBirthArr[2]
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "onSaveInstanceState()")
        val isDateOfBirthSelected = dateOfBirthMap.values.all { it != 0 }

        if (isDateOfBirthSelected) {
            val dateOfBirthArr = intArrayOf(
                dateOfBirthMap["year"]!!,
                dateOfBirthMap["month"]!!,
                dateOfBirthMap["day"]!!
            )

            outState.putIntArray(Constants.KEY_DOB, dateOfBirthArr)
        }
    }

    fun goToProfileActivity(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        val bundle = Bundle()

        val name = findViewById<EditText>(R.id.name_edit_text).text.toString()
        bundle.putString(Constants.KEY_NAME, name)

        val email = findViewById<EditText>(R.id.email_edit_text).text.toString()
        bundle.putString(Constants.KEY_EMAIL, email)

        val username = findViewById<EditText>(R.id.username_edit_text).text.toString()
        bundle.putString(Constants.KEY_USERNAME, username)

        val age = findViewById<EditText>(R.id.age_edit_text).text.toString()
        bundle.putString(Constants.KEY_AGE, age)

        val isDateOfBirthSelected = dateOfBirthMap.values.all { it != 0 }

        if (isDateOfBirthSelected) {
            val dateOfBirthArr = intArrayOf(
                dateOfBirthMap["year"]!!,
                dateOfBirthMap["month"]!!,
                dateOfBirthMap["day"]!!
            )

            bundle.putIntArray(Constants.KEY_DOB, dateOfBirthArr)

            val dob = LocalDate.of(
                dateOfBirthMap["year"]!!,
                dateOfBirthMap["month"]!!,
                dateOfBirthMap["day"]!!
            )

            val eighteenYears = dob.plusYears(18)
            val now = LocalDate.now()
            val oldEnough = now.isAfter(eighteenYears)

            if (oldEnough) {
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                val signupProblem = findViewById<TextView>(R.id.signup_problem_text)
                signupProblem.text = getString(R.string.not_old_enough)
            }
        } else {
            val signupProblem = findViewById<TextView>(R.id.signup_problem_text)
            signupProblem.text = getString(R.string.dob_not_selected)
        }
    }

    fun showDateOfBirthPickerDialog(view: View) {
        val newFragment = DateOfBirthPickerFragment()
        newFragment.show(supportFragmentManager, "dobPicker")
    }

    fun onDateClick(view: View) {
        println("Hello")
        val dobPicker = (view.parent as ViewGroup).getChildAt(0)
        dateOfBirthMap["day"] = (dobPicker as DatePicker).dayOfMonth
        dateOfBirthMap["month"] = (dobPicker as DatePicker).month + 1
        dateOfBirthMap["year"] = (dobPicker as DatePicker).year

        val dobFragment = supportFragmentManager
            .findFragmentByTag("dobPicker")
        (dobFragment as DialogFragment).dismiss()
    }

    fun getDateOfBirthMap(): MutableMap<String, Int> = dateOfBirthMap
}