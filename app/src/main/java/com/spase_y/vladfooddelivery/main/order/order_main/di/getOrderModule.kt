package com.spase_y.vladfooddelivery.main.order.order_main.di

import com.spase_y.vladfooddelivery.main.order.order_main.data.OrdersRepositoryImpl
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersRepository
import com.spase_y.vladfooddelivery.main.order.order_main.domain.impl.OrdersInteractorImpl
import com.spase_y.vladfooddelivery.main.order.order_main.ui.view_model.OrderViewModel
import org.koin.dsl.module


val getOrderModule = module {
    single {
        OrderViewModel(get())
    }
    single<OrdersInteractor> {
        OrdersInteractorImpl(get())
    }
    single<OrdersRepository> {
        OrdersRepositoryImpl(get(),get())
    }
}