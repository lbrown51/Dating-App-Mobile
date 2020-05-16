package com.ad340.datingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class ProfileActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val NUM_TABS = 3
    private val TAB_NAMES = arrayListOf("Profile", "Matches", "Settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val viewPager = findViewById<ViewPager2>(R.id.profile_pager)
        viewPager.adapter = ProfileTabAdapter(this, NUM_TABS, intent.extras)

        val profileTabs = findViewById<TabLayout>(R.id.profile_tabs)
        TabLayoutMediator(profileTabs, viewPager) { tab, position ->
            tab.text = TAB_NAMES[position]
        }.attach()

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
