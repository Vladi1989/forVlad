package com.spase_y.vladfooddelivery.root

import android.app.Application
import com.spase_y.vladfooddelivery.main.order.order_main.di.getOrderModule
import com.spase_y.vladfooddelivery.main.order.order_main.di.ordersModule
import com.spase_y.vladfooddelivery.root.di.rootModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@App)
            modules(
                rootModule,
                ordersModule,
                getOrderModule
            )
        }
    }
}
