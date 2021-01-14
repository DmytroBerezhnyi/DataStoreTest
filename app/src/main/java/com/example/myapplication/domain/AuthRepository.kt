package com.example.myapplication.domain

import com.example.myapplication.data.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun getUser(): Flow<User>

    suspend fun saveUser(user: User)
}