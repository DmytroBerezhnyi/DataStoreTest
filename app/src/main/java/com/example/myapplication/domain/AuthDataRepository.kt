package com.example.myapplication.domain

import com.example.myapplication.data.User
import kotlinx.coroutines.flow.Flow

class AuthDataRepository(
    private val userCache: BaseCache<User>
) : AuthRepository {

    override suspend fun getUser(): Flow<User> {
        return userCache.get()
    }

    override suspend fun saveUser(user: User) {
        userCache.put(user)
    }
}