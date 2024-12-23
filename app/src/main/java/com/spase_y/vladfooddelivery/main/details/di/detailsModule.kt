package com.spase_y.vladfooddelivery.main.details.di

import com.spase_y.vladfooddelivery.main.details.data.DetailsRepositoryImpl
import com.spase_y.vladfooddelivery.main.details.domain.api.DetailsInteractor
import com.spase_y.vladfooddelivery.main.details.domain.api.DetailsRepository
import com.spase_y.vladfooddelivery.main.details.domain.impl.DetailsInteractorImpl
import com.spase_y.vladfooddelivery.main.details.ui.view_model.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
    single<DetailsRepository> {
        DetailsRepositoryImpl()
    }
    single<DetailsInteractor> {
        DetailsInteractorImpl(get())
    }
    viewModel {
        DetailsViewModel(get())
    }
}