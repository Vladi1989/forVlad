package com.spase_y.vladfooddelivery.main.add_card.add_card.di

import android.content.Context
import android.content.SharedPreferences
import com.spase_y.vladfooddelivery.main.add_card.add_card.data.CardRepositoryImpl
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardInteractor
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardRepository
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.impl.CardInteractorImpl
import com.spase_y.vladfooddelivery.main.add_card.add_card.ui.view_model.CardViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cardModule = module {
    single { provideSharedPreferences(androidContext()) }
    single<CardRepository> { CardRepositoryImpl(get()) }
    single<CardInteractor> { CardInteractorImpl(get()) }
    viewModel { CardViewModel(get()) }
}

fun provideSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("cards_pref", Context.MODE_PRIVATE)
}