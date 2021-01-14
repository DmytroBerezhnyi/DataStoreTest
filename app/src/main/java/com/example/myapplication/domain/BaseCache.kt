package com.example.myapplication.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

abstract class BaseCache<T>(
    protected var sp: CacheSource<T>,
    private val cacheExpirationTime: Long = 3000000
) {

    val isCached: Deferred<Boolean>
        get() = GlobalScope.async {
            sp.contains()
        }

    @ExperimentalCoroutinesApi
    open val isExpired: Deferred<Boolean>
        get() = GlobalScope.async {
            val currentTime = System.currentTimeMillis()
            val lastUpdateTime = lastCacheUpdateTimeMillis
            val expired = currentTime - lastUpdateTime.getCompleted() > cacheExpirationTime

            if (expired) {
                evictAll()
            }

            expired
        }

    protected val lastCacheUpdateTimeMillis: Deferred<Long>
        get() = GlobalScope.async {
            sp.lastCacheUpdateTimeMillis.await()
        }

    suspend fun evictAll() {
        sp.remove()
    }

    protected fun setLastCacheUpdateTimeMillis() {
        val currentMillis = System.currentTimeMillis()
        sp.lastCacheUpdateTimeMillis = CompletableDeferred(currentMillis)
    }

    open suspend fun put(model: T): T {
        setLastCacheUpdateTimeMillis()
        sp.put(model)
        return model
    }

    open suspend fun get(): Flow<T> {
        return sp.get()
    }
}