package com.spase_y.vladfooddelivery.main.order.order_main.ui.model

import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem

sealed class OrderScreenState {
    object Loading : OrderScreenState()
    data class Success(val list: List<MenuItem>) : OrderScreenState()
    data class Error(val errorMessage: String) : OrderScreenState()
}