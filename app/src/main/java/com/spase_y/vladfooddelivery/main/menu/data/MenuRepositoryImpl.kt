package com.spase_y.vladfooddelivery.main.menu.data

import android.util.Log
import com.google.gson.Gson
import com.spase_y.vladfooddelivery.main.menu.data.model.ListMenu
import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuRepository
import com.spase_y.vladfooddelivery.main.menu.domain.model.MenuResponse
import com.spase_y.vladfooddelivery.root.Constants.GET_URL_FROM_BACK
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MenuRepositoryImpl : MenuRepository {
    private val client = OkHttpClient()
    private val gson = Gson()

    override fun getMenu(onResponse: (MenuResponse) -> Unit) {

        Log.d("MenuService", "Создание запроса с URL: $GET_URL_FROM_BACK")

        val request = Request.Builder()
            .url(GET_URL_FROM_BACK)
            .build()

        Log.d("MenuService", "Запрос построен, отправка запроса...")

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("MenuService", "Ошибка запроса: ${e.message}")
                onResponse(MenuResponse.Error("Ошибка запроса: ${e.message}"))
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("MenuService", "Ответ получен, код: ${response.code}")

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()

                    if (responseBody != null) {
                        try {
                            val listMenu = gson.fromJson(responseBody, ListMenu::class.java)
                            // Объединяем все элементы меню в один список
                            val allItems = listMenu.pizzaItems + listMenu.burgersItems + listMenu.wokItems + listMenu.sushiItems
                            Log.d("MenuService", "Полученные данные: ${allItems.joinToString { it.itemName }}")
                            onResponse(MenuResponse.Success(allItems))
                        } catch (e: Exception) {
                            Log.e("MenuService", "Ошибка парсинга JSON: ${e.message}")
                            onResponse(MenuResponse.Error("Ошибка парсинга данных: ${e.message}"))
                        }
                    } else {
                        Log.e("MenuService", "Пустой ответ от бэка")
                        onResponse(MenuResponse.Error("Пустой ответ от бэка"))
                    }
                } else {
                    Log.e("MenuService", "Не успешный ответ: ${response.code}")
                    onResponse(MenuResponse.Error("Не успешный ответ: ${response.code}"))
                }
            }
        })
    }
}