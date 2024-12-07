package com.spase_y.vladfooddelivery.main.menu.ui.model

import com.spase_y.vladfooddelivery.main.menu.data.model.Item

sealed class MenuScreenState {
    object Loading : MenuScreenState()
    data class Success(val items: List<Item>) : MenuScreenState()
    data class Error(val errorMessage: String) : MenuScreenState()
}