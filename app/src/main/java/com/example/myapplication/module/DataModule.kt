package com.example.myapplication.module

import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.myapplication.domain.AuthDataRepository
import com.example.myapplication.domain.BaseStorageSource
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.User
import com.example.myapplication.domain.AuthRepository
import com.example.myapplication.domain.BaseCache
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val USER_QUALIFIER = "user"

val dataModule = module {

    single {
        androidApplication().createDataStore(name = BuildConfig.APPLICATION_ID)
    }

    single<BaseCache<User>>(named(USER_QUALIFIER)) {
        val cacheSource = BaseStorageSource<User>(
            get(),
            preferencesKey("USER_NAME_KEY"),
            object : TypeToken<User>() {}.type
        )
        object : BaseCache<User>(cacheSource) {}
    }

    single<AuthRepository> {
        AuthDataRepository(
            get(named(USER_QUALIFIER))
        )
    }
}