package com.spase_y.vladfooddelivery.main.order.order_main.ui.model

import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem

interface OrderScreenState {
    object Loading: OrderScreenState
    class Success(val list: List<MenuItem>) : OrderScreenState
}