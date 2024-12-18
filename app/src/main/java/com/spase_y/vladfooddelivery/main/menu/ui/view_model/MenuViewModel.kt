package com.spase_y.vladfooddelivery.main.menu.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
) : ViewModel() {

    private val _menuLd = MutableLiveData<MenuScreenState>()
    val menuLd: LiveData<MenuScreenState> = _menuLd

    private val _orderLd = MutableLiveData<OrderScreenState>()
    val orderLd: LiveData<OrderScreenState> = _orderLd

    init {
        loadMenu()
    }

    fun loadMenu() {
        _menuLd.postValue(MenuScreenState.Loading)
        menuInteractor.getMenu { response ->
            when (response) {
                is MenuResponse.Success -> {
                    _menuLd.postValue(MenuScreenState.Success(response.items))
                }
                is MenuResponse.Error -> {
                    _menuLd.postValue(MenuScreenState.Error(response.errorMessage))
                }
            }
        }
    }

    fun addMenuItemToOrder(item: Item) {
        _orderLd.postValue(OrderScreenState.Loading)

        if (ordersInteractor.getAllList().size >= MAX_COUNT_ITEMS_TO_ORDER) {
            _orderLd.postValue(OrderScreenState.Error("Превышен максимум заказов"))
        } else {
            ordersInteractor.addItem(item)

            val updatedOrderList = ordersInteractor.getAllList()
            _orderLd.postValue(OrderScreenState.Success(updatedOrderList))
        }
    }

}



