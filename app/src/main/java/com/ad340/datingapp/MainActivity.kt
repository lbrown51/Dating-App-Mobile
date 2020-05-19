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
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val dateOfBirthMap = mutableMapOf(
        Constants.KEY_DAY to 1,
        Constants.KEY_MONTH to 1,
        Constants.KEY_YEAR to 1
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        val currentUser = auth.currentUser
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

            val dateOfBirthArr = getDateOfBirthArr()

            outState.putIntArray(Constants.KEY_DOB, dateOfBirthArr)
        }
    }

    fun goToProfileActivity(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        val bundle = Bundle()

        val name = findViewById<EditText>(R.id.name_edit_text).text.toString()
        val email = findViewById<EditText>(R.id.email_edit_text).text.toString()
        val occupation = findViewById<EditText>(R.id.occupation_edit_text).text.toString()
        val age = findViewById<EditText>(R.id.age_edit_text).text.toString()
        val description = findViewById<EditText>(R.id.description_text).text.toString()

        val signupProblem = findViewById<TextView>(R.id.signup_problem_text)
        val isDateOfBirthSelected = dateOfBirthMap.values.all { it != 0 }

        when {
            name == "" -> signupProblem.text = getString(R.string.no_name_entered)
            email == "" -> signupProblem.text = getString(R.string.no_email_entered)
            isEmailInvalid(email) -> signupProblem.text = getString(R.string.email_not_valid)
            occupation == "" -> signupProblem.text = getString(R.string.no_occupation_entered)
            age == "" -> signupProblem.text = getString(R.string.no_age_entered)
            description == "" -> signupProblem.text = getString(R.string.no_description_entered)
            !isDateOfBirthSelected -> signupProblem.text = getString(R.string.dob_not_selected)
            else -> {
                bundle.putString(Constants.KEY_NAME, name)
                bundle.putString(Constants.KEY_EMAIL, email)
                bundle.putString(Constants.KEY_OCCUPATION, occupation)
                bundle.putString(Constants.KEY_AGE, age)
                bundle.putString(Constants.KEY_DESCRIPTION, description)

                val dateOfBirthArr = getDateOfBirthArr()

                bundle.putIntArray(Constants.KEY_DOB, dateOfBirthArr)

                val dob = getDateOfBirthLocalDate()

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

    fun getDateOfBirthArr() = intArrayOf(
        dateOfBirthMap[Constants.KEY_YEAR] ?: 0,
        dateOfBirthMap[Constants.KEY_MONTH] ?: 0,
        dateOfBirthMap[Constants.KEY_DAY] ?: 0
    )

    fun getDateOfBirthLocalDate() = LocalDate.of(
        dateOfBirthMap[Constants.KEY_YEAR] ?: 0,
        dateOfBirthMap[Constants.KEY_MONTH] ?: 0,
        dateOfBirthMap[Constants.KEY_DAY] ?: 0
    )

    fun getDateOfBirthMap(): MutableMap<String, Int> = dateOfBirthMap
}