package com.spase_y.vladfooddelivery.main.menu.domain.api

import com.spase_y.vladfooddelivery.main.menu.domain.model.MenuResponse

interface MenuRepository {
    fun getMenu(onResponse: (MenuResponse) -> Unit)
}