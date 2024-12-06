package com.spase_y.vladfooddelivery.main.menu.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuInteractor
import com.spase_y.vladfooddelivery.main.menu.domain.model.MenuResponse
import com.spase_y.vladfooddelivery.main.menu.ui.model.MenuListScreenState
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.menu.ui.model.MenuScreenState
import com.spase_y.vladfooddelivery.root.Constants.MAX_COUNT_ITEMS_TO_ORDER

class MenuViewModel(
    private val ordersInteractor: OrdersInteractor,
    private val menuInteractor: MenuInteractor
) {
    private val menuLd = MutableLiveData<MenuScreenState?>()
    fun getMenuLd():LiveData<MenuScreenState?>{
        return menuLd
    }
    fun addMenuItemToOrder(item:MenuItem){
        menuLd.postValue(MenuScreenState.Loading)
        if(ordersInteractor.getAllList().size > MAX_COUNT_ITEMS_TO_ORDER){
            menuLd.postValue(MenuScreenState.Error)
        } else {
            menuLd.postValue(MenuScreenState.Succes)
            ordersInteractor.addItem(item)

        }
    }

    fun loadMenu() {
        menuLd.postValue(MenuScreenState.Loading)
        menuInteractor.getMenu { response ->
            when (response) {
                is MenuResponse.Success -> {
                    menuLd.postValue(object : MenuScreenState {
                        val items = response.items
                    })
                }
                is MenuResponse.Error -> {
                    menuLd.postValue(object : MenuScreenState {
                        val errorMessage = response.errorMessage
                    })
                }
            }
        }
    }


    fun clearMenuLD(){
        menuLd.postValue(null)
    }
}