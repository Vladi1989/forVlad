package com.spase_y.vladfooddelivery.main.order.order_main.ui.view_model

import androidx.lifecycle.MutableLiveData
import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.order.order_main.ui.model.OrderScreenState

class OrderViewModel (
    private val ordersInteractor: OrdersInteractor
) {
    val orderLd = MutableLiveData<OrderScreenState>()
    fun loadOrder() {
        orderLd.postValue(OrderScreenState.Loading)
        val list = ordersInteractor.getAllList()
        orderLd.postValue(OrderScreenState.Success(list))
    }
    fun removeItem(item: Item) {
        ordersInteractor.removeItem(item)
    }

    fun updateItem(oldMenuItem: Item, newMenuItem: Item) {
        ordersInteractor.replaceItem(oldMenuItem,newMenuItem)
    }
}