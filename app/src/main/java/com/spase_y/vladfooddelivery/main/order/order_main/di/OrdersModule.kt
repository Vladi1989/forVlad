package com.spase_y.vladfooddelivery.main.order.order_main.di

import com.spase_y.vladfooddelivery.main.menu.ui.view_model.MenuViewModel
import com.spase_y.vladfooddelivery.main.order.order_main.data.OrdersRepositoryImpl
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersRepository
import com.spase_y.vladfooddelivery.main.order.order_main.domain.impl.OrdersInteractorImpl
import org.koin.dsl.module

val ordersModule = module {
    single {
        MenuViewModel(get())
    }
    single<OrdersInteractor> {
        OrdersInteractorImpl(get())
    }
    single<OrdersRepository> {
        OrdersRepositoryImpl(get(),get())
    }
}