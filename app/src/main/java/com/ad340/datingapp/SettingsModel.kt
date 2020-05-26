package com.ad340.datingapp

import androidx.lifecycle.LiveData

class SettingsModel(private val settingsDao: SettingsDao) {
    private val auth = FirebaseAuthGetter.firebaseAuth!!
    private val currentUser = auth.currentUser

    val settings: LiveData<SettingsEntity> = settingsDao.getSettings(currentUser!!.uid)

    suspend fun insert(settings: SettingsEntity) {
        settingsDao.insert(settings)
    }

    suspend fun update(settings: SettingsEntity) {
        settingsDao.update(settings)
    }
}