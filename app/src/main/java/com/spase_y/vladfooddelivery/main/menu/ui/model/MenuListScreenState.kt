package com.spase_y.vladfooddelivery.main.menu.ui.model

import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem

interface MenuListScreenState {
    object Loading : MenuListScreenState

    data class Error(val errorMessage: String?) : MenuListScreenState
    data class Succes(val message: String) : MenuListScreenState
    data class MenuLoaded(val items: List<MenuItem>) : MenuListScreenState
}