package com.spase_y.vladfooddelivery.root.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.math.sin

val rootModule = module {
    single {
        Gson()
    }
    single<SharedPreferences> {
        androidContext().getSharedPreferences("TAG",Context.MODE_PRIVATE)
    }
}