package com.spase_y.vladfooddelivery.main.menu.domain.impl

import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuInteractor
import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuRepository
import com.spase_y.vladfooddelivery.main.menu.domain.model.MenuResponse

class MenuInteractorImpl(private val repository: MenuRepository): MenuInteractor {
    override fun getMenu(onResponse: (MenuResponse) -> Unit) {
        repository.getMenu { response ->
            onResponse(response)
        }
    }
}