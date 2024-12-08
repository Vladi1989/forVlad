package com.spase_y.vladfooddelivery.main.menu.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuInteractor
import com.spase_y.vladfooddelivery.main.menu.domain.model.MenuResponse
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.menu.ui.model.MenuScreenState
import com.spase_y.vladfooddelivery.main.order.order_main.ui.model.OrderScreenState
import com.spase_y.vladfooddelivery.root.Constants.MAX_COUNT_ITEMS_TO_ORDER

class MenuViewModel(
    private val ordersInteractor: OrdersInteractor,
    private val menuInteractor: MenuInteractor
) {
    private val menuLd = MutableLiveData<MenuScreenState?>()
    fun getMenuLd(): LiveData<MenuScreenState?> {
        return menuLd
    }

    private val orderLd = MutableLiveData<OrderScreenState?>()
    fun getOrderLd(): LiveData<OrderScreenState?> {
        return orderLd
    }

    fun addMenuItemToOrder(item: Item) {
        orderLd.postValue(OrderScreenState.Loading)

        if (ordersInteractor.getAllList().size >= MAX_COUNT_ITEMS_TO_ORDER) {
            orderLd.postValue(OrderScreenState.Error("Maximum items exceeded"))
        } else {
            ordersInteractor.addItem(item)

            val updatedOrderList = ordersInteractor.getAllList()  // Это ваша переменная, которая содержит обновленный список

            orderLd.postValue(OrderScreenState.Success(updatedOrderList))
        }
    }

    // Загрузка меню
    fun loadMenu() {
        menuLd.postValue(MenuScreenState.Loading)
        menuInteractor.getMenu { response ->
            when (response) {
                is MenuResponse.Success -> {
                    menuLd.postValue(MenuScreenState.Success(response.items))
                }
                is MenuResponse.Error -> {
                    menuLd.postValue(MenuScreenState.Error(response.errorMessage))
                }
            }
        }
    }

    // Очистка LiveData для меню
    fun clearMenuLD() {
        menuLd.postValue(null)
    }

    // Очистка LiveData для добавления в заказ
    fun clearOrderLD() {
        orderLd.postValue(null)
    }
}



