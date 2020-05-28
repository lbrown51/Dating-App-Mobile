package com.ad340.datingapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings_table WHERE uid = :uid")
    fun getSettings(uid: String): LiveData<SettingsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(settings: SettingsEntity)

    @Update
    suspend fun update(settings: SettingsEntity)

}