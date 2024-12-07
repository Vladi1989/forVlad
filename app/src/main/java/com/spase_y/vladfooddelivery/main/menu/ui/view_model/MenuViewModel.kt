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
    private val menuInteractor: MenuInteractor
) {
    // LiveData для управления состоянием меню
    private val menuLiveData = MutableLiveData<MenuScreenState>()

    fun getMenuLiveData(): LiveData<MenuScreenState> = menuLiveData

    // Метод для загрузки меню
    fun loadMenu() {
        menuLiveData.postValue(MenuScreenState.Loading)
        menuInteractor.getMenu { response ->
            when (response) {
                is MenuResponse.Success -> {
                    menuLiveData.postValue(MenuScreenState.Success(response.items))
                }
                is MenuResponse.Error -> {
                    menuLiveData.postValue(MenuScreenState.Error(response.errorMessage))
                }
            }
        }
    }

    fun clearMenuState() {
        menuLiveData.postValue(null)
    }
}