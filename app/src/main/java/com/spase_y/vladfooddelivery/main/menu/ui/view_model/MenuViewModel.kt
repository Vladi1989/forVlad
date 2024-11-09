package com.spase_y.vladfooddelivery.main.menu.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersInteractor
import com.spase_y.vladfooddelivery.main.menu.ui.model.MenuScreenState

class MenuViewModel(
    private val ordersInteractor: OrdersInteractor
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
    fun clearMenuLD(){
        menuLd.postValue(null)
    }
    companion object{
        const val MAX_COUNT_ITEMS_TO_ORDER = 20
    }
}