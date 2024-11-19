package com.spase_y.vladfooddelivery.root.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.spase_y.vladfooddelivery.root.ui.view_model.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val rootModule = module {
    single {
        Gson()
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences("TAG",Context.MODE_PRIVATE)
    }
    viewModel {
        MainViewModel(get())
    }
}