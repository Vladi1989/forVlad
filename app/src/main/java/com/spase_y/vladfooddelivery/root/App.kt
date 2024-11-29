package com.spase_y.vladfooddelivery.root

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.spase_y.vladfooddelivery.loading.get_code.di.getCodeModule
import com.spase_y.vladfooddelivery.main.add_card.add_card.di.cardModule
import com.spase_y.vladfooddelivery.main.order.order_main.di.getOrderModule
import com.spase_y.vladfooddelivery.main.order.order_main.di.ordersModule
import com.spase_y.vladfooddelivery.root.di.loginUserModule
import com.spase_y.vladfooddelivery.root.di.rootModule
import com.spase_y.vladfooddelivery.root.di.themeModule
import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeInteractor
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                rootModule,
                ordersModule,
                getOrderModule,
                getCodeModule,
                loginUserModule,
                themeModule,
                cardModule,

            )
        }

        val themeInteractor: ThemeInteractor by inject()
        applyTheme(themeInteractor.getCurrentTheme())
    }

    private fun applyTheme(theme: ThemeInteractor.ApplicationTheme) {
        AppCompatDelegate.setDefaultNightMode(
            when (theme) {
                ThemeInteractor.ApplicationTheme.DAY -> AppCompatDelegate.MODE_NIGHT_NO
                ThemeInteractor.ApplicationTheme.NIGHT -> AppCompatDelegate.MODE_NIGHT_YES
            }
        )
    }
}
