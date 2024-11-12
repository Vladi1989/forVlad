package com.spase_y.vladfooddelivery.loading.get_code.di

import android.content.Context
import android.content.SharedPreferences
import com.spase_y.vladfooddelivery.loading.get_code.ui.view_model.GetCodeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val getCodeModule = module {
    single { provideSharedPreferences(get()) }
    viewModel {GetCodeViewModel(get()) }
}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("APP_PREFS",Context.MODE_PRIVATE)
}