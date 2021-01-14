package com.example.myapplication.domain

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface CacheSource<T> {

  var lastCacheUpdateTimeMillis: Deferred<Long>

    suspend fun contains(): Boolean

    suspend fun remove()

    suspend fun put(model: T)

    suspend fun get(): Flow<T>
}