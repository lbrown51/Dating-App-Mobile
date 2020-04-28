package com.ad340.datingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val thanksText = findViewById<TextView>(R.id.signup_thanks_text)

        val msgText = getString(R.string.signup_thanks)
        val msg = StringBuilder(msgText)
        msg.append("\n")

        val b = intent.extras

        if (b != null) {
            if (b.containsKey(Constants.KEY_USERNAME)) {
                val username = b.getString(Constants.KEY_USERNAME)
                msg.append(username)
            }
        }

        thanksText.text = msg
    }

    fun onDateClick() {}
}
