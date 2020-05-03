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
        val nameText = findViewById<TextView>(R.id.profile_name_age_text)

        val msg = StringBuilder()
        val b = intent.extras

        if (b != null) {
            if (b.containsKey(Constants.KEY_NAME)) {
                val username = b.getString(Constants.KEY_NAME)
                msg.append(username)
            }
        }

        nameText.text = msg
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
