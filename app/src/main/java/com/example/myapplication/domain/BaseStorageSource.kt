package com.example.myapplication.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class BaseStorageSource<T>(
    private val dataStore: DataStore<Preferences>,
    private val KEY: Preferences.Key<String>,
    private val mType: Type
) : CacheSource<T> {

    private val KEY_TIME = preferencesKey<Long>(KEY.name + " time")

    override var lastCacheUpdateTimeMillis: Deferred<Long>
        get() = GlobalScope.async {
            dataStore.data.map {
                it[KEY_TIME]
            }.first() ?: 0
        }
        set(currentMillis) {
            GlobalScope.launch {
                dataStore.edit {
                    it[KEY_TIME] = currentMillis.await()
                }
            }
        }


    override suspend fun contains(): Boolean {
        var result = false
        dataStore.data.map {
            if (!it[KEY].isNullOrEmpty()) {
                result = true
            }
        }
        return result
    }

    override suspend fun remove() {
        dataStore.edit {
            it[KEY] = ""
        }
    }

    override suspend fun put(model: T) {
        dataStore.edit {
            it[KEY] = Gson().toJson(model, mType)
        }
    }

    override suspend fun get(): Flow<T> {
        return dataStore.data.map {
            Gson().fromJson(it[KEY], mType)
        }
    }
}