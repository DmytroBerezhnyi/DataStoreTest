package com.example.myapplication.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.User
import com.example.myapplication.domain.AuthRepository
import kotlinx.coroutines.launch

class MainVM(private val authRepository: AuthRepository) : ViewModel() {

    val user = liveData {
        emitSource(authRepository.getUser().asLiveData())
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            authRepository.saveUser(user)
        }
    }
}