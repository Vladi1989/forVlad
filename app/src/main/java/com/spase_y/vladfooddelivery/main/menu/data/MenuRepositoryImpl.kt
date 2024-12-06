package com.spase_y.vladfooddelivery.main.menu.data

import com.google.gson.Gson
import com.spase_y.vladfooddelivery.NetworkClient
import com.spase_y.vladfooddelivery.main.menu.data.model.ListMenu
import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuRepository
import com.spase_y.vladfooddelivery.main.menu.domain.model.MenuResponse
import com.spase_y.vladfooddelivery.root.Constants.GET_URL_FROM_BACK

class MenuRepositoryImpl(private val networkClient: NetworkClient): MenuRepository {
    override fun getMenu(onResponse: (MenuResponse) -> Unit) {
        networkClient.makeRequestAsync(
            url = GET_URL_FROM_BACK,
            onSuccess = { jsonResponse ->
                try {
                    val gson = Gson()
                    val listMenu = gson.fromJson(jsonResponse, ListMenu::class.java)
                    onResponse(MenuResponse.Success(listMenu.items))
                } catch (e: Exception) {
                    onResponse(MenuResponse.Error("Ошибка парсинга данных: ${e.message}"))
                }
            },
            onFailure = { error ->
                onResponse(MenuResponse.Error("Ошибка сети: $error"))
            }
        )
    }
}