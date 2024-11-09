package com.spase_y.vladfooddelivery.main.order.order_main.domain.impl

import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersRepository

class OrdersInteractorImpl(private val ordersRepository: OrdersRepository): OrdersInteractor {
    override fun addItem(item: MenuItem) {
        ordersRepository.addItem(item)
    }

    override fun removeItem(item: MenuItem) {
        ordersRepository.removeItem(item)

    }

    override fun clearAllList() {
        ordersRepository.clearAllList()
    }

    override fun getAllList(): List<MenuItem> {
       return ordersRepository.getAllList()

    }
}