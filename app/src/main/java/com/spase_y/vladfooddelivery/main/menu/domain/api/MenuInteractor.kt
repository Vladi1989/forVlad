package com.spase_y.vladfooddelivery.main.menu.domain.api

import com.spase_y.vladfooddelivery.main.menu.domain.model.MenuResponse

interface MenuInteractor {
    fun getMenu(onResponse: (MenuResponse) -> Unit)
}