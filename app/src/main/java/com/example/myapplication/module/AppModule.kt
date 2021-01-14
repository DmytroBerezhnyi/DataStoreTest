package com.example.myapplication.module

import com.example.myapplication.view.MainVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainVM(get()) }
}