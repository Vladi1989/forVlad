package com.spase_y.vladfooddelivery.main.order.order_main.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.order.order_main.ui.model.OrderScreenState
import com.spase_y.vladfooddelivery.root.Constants.MAX_COUNT_ITEMS_TO_ORDER

class OrderViewModel(
    private val ordersInteractor: OrdersInteractor
) {
    // LiveData для управления состоянием корзины
    private val orderLiveData = MutableLiveData<OrderScreenState>()

    fun getOrderLiveData(): LiveData<OrderScreenState> = orderLiveData

    // Метод для загрузки корзины
    fun loadOrder() {
        orderLiveData.postValue(OrderScreenState.Loading)
        val list = ordersInteractor.getAllList()
        orderLiveData.postValue(OrderScreenState.Success(list))
    }

    // Добавление товара в корзину
    fun addMenuItemToOrder(item: MenuItem) {
        if (ordersInteractor.getAllList().size >= MAX_COUNT_ITEMS_TO_ORDER) {
            orderLiveData.postValue(OrderScreenState.Error("Максимальное количество товаров в корзине."))
        } else {
            ordersInteractor.addItem(item)
            loadOrder() // Обновляем состояние корзины
        }
    }

    fun removeItem(item: MenuItem) {
        ordersInteractor.removeItem(item)
        loadOrder()
    }

    fun updateItem(oldMenuItem: MenuItem, newMenuItem: MenuItem) {
        ordersInteractor.replaceItem(oldMenuItem, newMenuItem)
        loadOrder()
    }
}