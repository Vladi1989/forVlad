package com.spase_y.vladfooddelivery.main.order.order_main.domain.api

import com.spase_y.vladfooddelivery.main.menu.data.model.Item

interface OrdersInteractor {
    fun addItem(item: Item)
    fun removeItem(item:Item)
    fun clearAllList()
    fun getAllList():List<Item>
    fun replaceItem(oldItem: Item,newItem: Item)
}