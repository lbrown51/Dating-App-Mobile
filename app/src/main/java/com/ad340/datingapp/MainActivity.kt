package com.ad340.datingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName


    private val dateOfBirthMap = mutableMapOf(
        Constants.KEY_DAY to 0,
        Constants.KEY_MONTH to 0,
        Constants.KEY_YEAR to 0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        findViewById<EditText>(R.id.name_edit_text).setText("")
        findViewById<EditText>(R.id.email_edit_text).setText("")
        findViewById<EditText>(R.id.username_edit_text).setText("")
        findViewById<EditText>(R.id.age_edit_text).setText("")

        findViewById<Button>(R.id.date_of_birth_btn).setText(R.string.select_dob_text)
        dateOfBirthMap[Constants.KEY_YEAR] = 0
        dateOfBirthMap[Constants.KEY_MONTH] = 0
        dateOfBirthMap[Constants.KEY_DAY] = 0
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState.containsKey(Constants.KEY_DOB)) {
            val dateOfBirthArr= savedInstanceState.getIntArray(Constants.KEY_DOB)
            if (dateOfBirthArr != null) {
                dateOfBirthMap[Constants.KEY_YEAR] = dateOfBirthArr[0]
                dateOfBirthMap[Constants.KEY_MONTH] = dateOfBirthArr[1]
                dateOfBirthMap[Constants.KEY_DAY] = dateOfBirthArr[2]
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState);

        val isDateOfBirthSelected = dateOfBirthMap.values.all { it != 0 }

        if (isDateOfBirthSelected) {
            val dateOfBirthArr = intArrayOf(
                dateOfBirthMap[Constants.KEY_YEAR]!!,
                dateOfBirthMap[Constants.KEY_MONTH]!!,
                dateOfBirthMap[Constants.KEY_DAY]!!
            )

            outState.putIntArray(Constants.KEY_DOB, dateOfBirthArr)
        }
    }

    fun goToProfileActivity(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        val bundle = Bundle()

        val name = findViewById<EditText>(R.id.name_edit_text).text.toString()
        val email = findViewById<EditText>(R.id.email_edit_text).text.toString()
        val username = findViewById<EditText>(R.id.username_edit_text).text.toString()
        val age = findViewById<EditText>(R.id.age_edit_text).text.toString()

        val signupProblem = findViewById<TextView>(R.id.signup_problem_text)
        val isDateOfBirthSelected = dateOfBirthMap.values.all { it != 0 }

        when {
            name == "" -> signupProblem.text = getString(R.string.no_name_entered)
            email == "" -> signupProblem.text = getString(R.string.no_email_entered)
            isEmailInvalid(email) -> signupProblem.text = getString(R.string.email_not_valid)
            username == "" -> signupProblem.text = getString(R.string.no_username_entered)
            age == "" -> signupProblem.text = getString(R.string.no_age_entered)
            !isDateOfBirthSelected -> signupProblem.text = getString(R.string.dob_not_selected)
            else -> {
                bundle.putString(Constants.KEY_NAME, name)
                bundle.putString(Constants.KEY_EMAIL, email)
                bundle.putString(Constants.KEY_USERNAME, username)
                bundle.putString(Constants.KEY_AGE, age)

                val dateOfBirthArr = intArrayOf(
                    dateOfBirthMap[Constants.KEY_YEAR]!!,
                    dateOfBirthMap[Constants.KEY_MONTH]!!,
                    dateOfBirthMap[Constants.KEY_DAY]!!
                )

                bundle.putIntArray(Constants.KEY_DOB, dateOfBirthArr)

                val dob = LocalDate.of(
                    dateOfBirthMap[Constants.KEY_YEAR]!!,
                    dateOfBirthMap[Constants.KEY_MONTH]!!,
                    dateOfBirthMap[Constants.KEY_DAY]!!
                )

                val eighteenYears = dob.plusYears(Constants.LEGAL_YEAR)
                val now = LocalDate.now()
                val oldEnough = now.isAfter(eighteenYears)

                if (oldEnough) {
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    signupProblem.text = getString(R.string.not_old_enough)
                }
            }
        }
    }

    fun showDateOfBirthPickerDialog(view: View) {
        val newFragment = DateOfBirthPickerFragment()
        newFragment.show(supportFragmentManager, Constants.DOB_PICKER)
    }

    fun onDateClick(view: View) {
        val dobPicker = (view.parent as ViewGroup).getChildAt(0)

        (dobPicker as DatePicker).let {
            dateOfBirthMap[Constants.KEY_DAY] = dobPicker.dayOfMonth
            dateOfBirthMap[Constants.KEY_MONTH] = dobPicker.month + 1
            dateOfBirthMap[Constants.KEY_YEAR] = dobPicker.year

            val dateOfBirthText = getString(R.string.date_of_birth)
            val dob = StringBuilder(dateOfBirthText)
            dob.append(" ", dobPicker.month + 1, "/")
            dob.append(dobPicker.dayOfMonth, "/")
            dob.append(dobPicker.year)

            val dobButton = findViewById<Button>(R.id.date_of_birth_btn)
            dobButton.text = dob
        }

        val dobFragment = supportFragmentManager
            .findFragmentByTag(Constants.DOB_PICKER)
        (dobFragment as DialogFragment).dismiss()

    }

    fun isEmailInvalid(email: String): Boolean {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun getDateOfBirthMap(): MutableMap<String, Int> = dateOfBirthMap
}