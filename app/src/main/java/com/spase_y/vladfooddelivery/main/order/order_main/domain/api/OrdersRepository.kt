package com.spase_y.vladfooddelivery.main.order.order_main.domain.api

import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem

interface OrdersRepository {
    fun addItem(item: MenuItem)
    fun removeItem(item: MenuItem)
    fun clearAllList()
    fun getAllList():List<MenuItem>
    fun replaceItem(oldItem: MenuItem,newItem: MenuItem)
}