package com.spase_y.vladfooddelivery.main.menu.di

import com.spase_y.vladfooddelivery.main.menu.data.MenuRepositoryImpl
import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuInteractor
import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuRepository
import com.spase_y.vladfooddelivery.main.menu.domain.impl.MenuInteractorImpl
import com.spase_y.vladfooddelivery.main.menu.ui.view_model.MenuViewModel
import org.koin.dsl.module

val menuModule = module {
    single<MenuViewModel> {
        MenuViewModel(get(), get())
    }
    single<MenuInteractor> {
        MenuInteractorImpl(get())
    }
    single<MenuRepository> {
        MenuRepositoryImpl()
    }
}