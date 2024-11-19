package com.spase_y.vladfooddelivery.root.di

import com.spase_y.vladfooddelivery.root.theme.data.ThemeRepositoryImpl
import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeInteractor
import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeRepository
import com.spase_y.vladfooddelivery.root.theme.domain.impl.ThemeInteractorImpl
import org.koin.dsl.module

val themeModule = module {
    single<ThemeInteractor> {
        ThemeInteractorImpl(get())
    }
    single<ThemeRepository> {
        ThemeRepositoryImpl(get())
    }
}



