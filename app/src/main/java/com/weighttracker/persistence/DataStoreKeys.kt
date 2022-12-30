package com.weighttracker.persistence

import androidx.datastore.preferences.core.doublePreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreKeys @Inject constructor() {
    val weight by lazy { doublePreferencesKey(name = "weight") }
}