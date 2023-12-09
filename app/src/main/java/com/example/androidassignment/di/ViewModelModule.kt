package com.example.androidassignment.di

import com.example.androidassignment.ui.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TimerViewModel() }
}