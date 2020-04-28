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
