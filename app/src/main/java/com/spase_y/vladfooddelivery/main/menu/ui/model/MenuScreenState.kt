package com.spase_y.vladfooddelivery.main.menu.ui.model

import com.spase_y.vladfooddelivery.main.menu.data.model.ListMenu

interface MenuScreenState {
    object Loading : MenuScreenState
    class Success(val menu: ListMenu) : MenuScreenState
    class Error(val errorMessage: String) : MenuScreenState
}

