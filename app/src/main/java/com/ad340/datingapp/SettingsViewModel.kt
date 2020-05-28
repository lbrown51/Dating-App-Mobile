package com.ad340.datingapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application): AndroidViewModel(application) {

    private val dataModel: SettingsModel

    val settings: LiveData<SettingsEntity>

    init {
        val settingsDao = SettingsRoomDatabase.getDatabase(application).settingsDao()
        dataModel = SettingsModel(settingsDao)
        settings = dataModel.settings
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(settings: SettingsEntity) = viewModelScope.launch(Dispatchers.IO) {
        dataModel.insert(settings)
    }

    /**
     * Launching a new coroutine to update the data in a non-blocking way
     */
    fun update(settings: SettingsEntity) = viewModelScope.launch(Dispatchers.IO) {
        dataModel.update(settings)
    }

}