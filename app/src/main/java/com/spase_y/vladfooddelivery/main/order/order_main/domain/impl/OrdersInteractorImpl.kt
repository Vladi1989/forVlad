package com.spase_y.vladfooddelivery.main.order.order_main.domain.impl

import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersRepository

class OrdersInteractorImpl(private val ordersRepository: OrdersRepository): OrdersInteractor {
    override fun addItem(item: Item) {
        ordersRepository.addItem(item)
    }

    override fun removeItem(item: Item) {
        ordersRepository.removeItem(item)

    }

    override fun clearAllList() {
        ordersRepository.clearAllList()
    }

    override fun getAllList(): List<Item> {
       return ordersRepository.getAllList()

    }

    override fun replaceItem(oldItem: Item, newItem: Item) {
        ordersRepository.replaceItem(oldItem,newItem)
    }


}