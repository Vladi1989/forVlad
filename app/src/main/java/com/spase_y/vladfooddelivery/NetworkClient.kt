package com.spase_y.vladfooddelivery

import android.util.Log
import com.google.gson.Gson
import com.spase_y.vladfooddelivery.main.menu.data.model.ListMenu
import com.spase_y.vladfooddelivery.root.Constants.GET_URL_FROM_BACK
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class NetworkClient {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun makeRequestAsync(url: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit){
        Log.d("MenuService", "Создание запроса с URL: $url")

        val request = Request.Builder()
            .url(GET_URL_FROM_BACK)
            .build()

        Log.d("MenuService", "Запрос построен, отправка запроса...")

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                Log.e("MenuService", "Ошибка запроса: ${e.message}")
                onFailure("Ошибка запроса: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("MenuService", "Ответ получен, код: ${response.code}")
                if(response.isSuccessful){
                    val responseBody = response.body?.string()
                    if(responseBody != null){
                        try {
                            val listMenu = gson.fromJson(responseBody, ListMenu::class.java)
                            Log.d("MenuService", "Полученные данные: ${listMenu.items.joinToString { it.item_name }}")
                            onSuccess(listMenu.items.toString())
                        } catch (e: Exception) {
                            Log.e("MenuService", "Ошибка парсинга JSON: ${e.message}")
                            onFailure("Ошибка парсинга JSON: ${e.message}")
                        }
                    } else {
                        Log.e("MenuService", "Пустой ответ от бэка")
                        onFailure("Пустой ответ от бэка")
                    }
                } else {
                    Log.e("MenuService", "Не успешный ответ: ${response.code}")
                    onFailure("Не успешный ответ: ${response.code}")
                }
            }
        })
    }
}