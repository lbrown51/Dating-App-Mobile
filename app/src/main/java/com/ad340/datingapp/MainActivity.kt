package com.ad340.datingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToProfileActivity() {
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

        val dateOfBirth = findViewById<DatePicker>(R.id.date_of_birth)
        val dateOfBirthArr = intArrayOf(
            dateOfBirth.year,
            dateOfBirth.month + 1,
            dateOfBirth.dayOfMonth
        )

        val dob = LocalDate.of(
            dateOfBirth.year,
            dateOfBirth.month,
            dateOfBirth.dayOfMonth
        )
        bundle.putIntArray(Constants.KEY_DOB, dateOfBirthArr)

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
    }
}
