package com.spase_y.vladfooddelivery.main.add_card.add_card.di

import com.spase_y.vladfooddelivery.main.add_card.add_card.data.CardRepositoryImpl
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardInteractor
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardRepository
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.impl.CardInteractorImpl
import com.spase_y.vladfooddelivery.main.add_card.add_card.ui.view_model.CardViewModel
import org.koin.dsl.module

val cardModule = module{
    single<CardRepository> { CardRepositoryImpl() }

    single<CardInteractor> {CardInteractorImpl(get())}

    single { CardViewModel(get())}
}

