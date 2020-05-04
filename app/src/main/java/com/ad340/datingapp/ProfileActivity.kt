package com.ad340.datingapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ProfileActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val nameAgeText = findViewById<TextView>(R.id.profile_name_age_text)
        val occupationText = findViewById<TextView>(R.id.profile_occupation_text)
        val descriptionText = findViewById<TextView>(R.id.profile_desc_text)

        val b = intent.extras

        if (b != null) {

            val nameAgeStr = StringBuilder()
            if (b.containsKey(Constants.KEY_NAME)) {
                val name = b.getString(Constants.KEY_NAME)
                nameAgeStr.append(name)

                if (b.containsKey(Constants.KEY_AGE)) {
                    val age = b.getString(Constants.KEY_AGE)
                    nameAgeStr.append(", ")
                    nameAgeStr.append(age)
                }

                nameAgeText.text = nameAgeStr
            }

            val occupationStr = StringBuilder()
            if (b.containsKey(Constants.KEY_OCCUPATION)) {
                val occupation = b.getString(Constants.KEY_OCCUPATION)
                occupationStr.append(occupation)

                occupationText.text = occupationStr
            }

            val descriptionStr = StringBuilder()
            if (b.containsKey(Constants.KEY_DESCRIPTION)) {
                val description = b.getString(Constants.KEY_DESCRIPTION)
                descriptionStr.append(description)

                descriptionText.text = descriptionStr
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed() // this can go before or after your stuff below
        // do your stuff when the back button is pressed
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        // super.onBackPressed(); calls finish(); for you

        // clear your SharedPreferences
        getSharedPreferences("preferenceName", 0).edit().clear().apply()
    }

    fun onDateClick() {}
}
