package com.ad340.datingapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SettingsEntity::class], version = 1, exportSchema = false)
public abstract class SettingsRoomDatabase: RoomDatabase() {

    abstract fun settingsDao(): SettingsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SettingsRoomDatabase? = null

        fun getDatabase(context: Context): SettingsRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SettingsRoomDatabase::class.java,
                    "settings_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}