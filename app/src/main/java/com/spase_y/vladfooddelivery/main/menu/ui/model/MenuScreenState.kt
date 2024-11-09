package com.spase_y.vladfooddelivery.main.menu.ui.model

interface MenuScreenState {
    object Loading:MenuScreenState
    object Error:MenuScreenState
    object Succes:MenuScreenState
}