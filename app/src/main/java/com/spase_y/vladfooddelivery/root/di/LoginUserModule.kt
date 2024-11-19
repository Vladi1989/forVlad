package com.spase_y.vladfooddelivery.root.di

import com.spase_y.vladfooddelivery.root.login_status.data.LoginUserRepositoryImpl
import com.spase_y.vladfooddelivery.root.login_status.domain.api.LoginUserInteractor
import com.spase_y.vladfooddelivery.root.login_status.domain.api.LoginUserRepository
import com.spase_y.vladfooddelivery.root.login_status.domain.impl.LoginUserInteractorImpl
import org.koin.dsl.module

val loginUserModule = module {
     single<LoginUserInteractor> {
          LoginUserInteractorImpl(get())
     }
     single<LoginUserRepository> {
          LoginUserRepositoryImpl(get())
     }
}


