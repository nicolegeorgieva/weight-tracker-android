package com.weighttracker.persistence.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreKeys @Inject constructor() {
    val weight by lazy { doublePreferencesKey(name = "weight") }
    val height by lazy { doublePreferencesKey(name = "height") }
    val kgSelected by lazy { booleanPreferencesKey(name = "kg") }
    val mSelected by lazy { booleanPreferencesKey(name = "m") }
    val quote by lazy { stringPreferencesKey(name = "quote") }
    val weightGoal by lazy { doublePreferencesKey(name = "weight_goal") }
}