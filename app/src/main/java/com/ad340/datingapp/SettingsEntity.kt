package com.ad340.datingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
class SettingsEntity (
    @PrimaryKey val uid: String,
    var gender: String,
    var isPublic: Boolean,
    var minAge: Int,
    var maxAge: Int,
    var maximumSearchDistance: Int
)