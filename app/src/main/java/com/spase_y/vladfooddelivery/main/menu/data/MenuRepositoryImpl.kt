package com.spase_y.vladfooddelivery.main.menu.data

import android.util.Log
import com.google.gson.Gson
import com.spase_y.vladfooddelivery.main.menu.data.model.ListMenu
import com.spase_y.vladfooddelivery.main.menu.domain.api.MenuRepository
import com.spase_y.vladfooddelivery.main.menu.domain.model.MenuResponse
import com.spase_y.vladfooddelivery.root.Constants.GET_URL_FROM_BACK
import okhttp3.OkHttpClient
import okhttp3.*
import java.io.IOException

class MenuRepositoryImpl : MenuRepository {

    private val client = OkHttpClient()
    private val gson = Gson()

    override fun getMenu(onResponse: (MenuResponse) -> Unit) {
        Log.d("MenuRepository", "Создание запроса к URL: $GET_URL_FROM_BACK")

        val request = Request.Builder()
            .url(GET_URL_FROM_BACK)
            .build()

        Log.d("MenuRepository", "Запрос построен, отправка...")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MenuRepository", "Ошибка сети: ${e.message}")
                onResponse(MenuResponse.Error("Ошибка сети: ${e.message}"))
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("MenuRepository", "Ответ получен, код: ${response.code}")
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        try {
                            val listMenu = gson.fromJson(responseBody, ListMenu::class.java)
                            Log.d(
                                "MenuRepository",
                                "Полученные данные: ${listMenu.items.joinToString { it.item_name }}"
                            )
                            onResponse(MenuResponse.Success(listMenu.items))
                        } catch (e: Exception) {
                            Log.e("MenuRepository", "Ошибка парсинга данных: ${e.message}")
                            onResponse(MenuResponse.Error("Ошибка парсинга данных: ${e.message}"))
                        }
                    } else {
                        Log.e("MenuRepository", "Пустой ответ от сервера")
                        onResponse(MenuResponse.Error("Пустой ответ от сервера"))
                    }
                } else {
                    Log.e("MenuRepository", "Неуспешный ответ, код: ${response.code}")
                    onResponse(MenuResponse.Error("Неуспешный ответ, код: ${response.code}"))
                }
            }
        })
    }
}