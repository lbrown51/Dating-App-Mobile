package com.ad340.datingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
class SettingsEntity (
    @PrimaryKey val uid: String,
    val gender: String,
    val isPublic: Boolean,
    val ageRange: IntArray,
    val maximumSearchDistance: Int
)