package com.spase_y.vladfooddelivery.main.menu.ui.model

import com.spase_y.vladfooddelivery.main.menu.data.model.Item

interface MenuScreenState {
    object Loading : MenuScreenState
    class Success(val items: List<Item>) : MenuScreenState
    class Error(val errorMessage: String) : MenuScreenState
}

