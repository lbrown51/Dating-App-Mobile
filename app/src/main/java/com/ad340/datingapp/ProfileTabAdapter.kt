package com.ad340.datingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileTabAdapter(activity: AppCompatActivity, val itemsCount: Int, val bundle: Bundle?):
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> Matches()
            2 -> Settings()
            else -> Profile(bundle)
        }
    }
}