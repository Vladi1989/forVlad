package com.spase_y.vladfooddelivery.main.order.order_main.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersRepository

class OrdersRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
): OrdersRepository {
    override fun addItem(item: MenuItem) {
        val currentList = getAllList().toMutableList()
        currentList.add(item)
        val result = gson.toJson(currentList)
        sharedPreferences.edit().putString(CURRENT_ALL_LIST,result).apply()
    }

    override fun removeItem(item: MenuItem) {
        val currentList = getAllList().toMutableList()
        currentList.remove(item)
        val result = gson.toJson(currentList)
        sharedPreferences.edit().putString(CURRENT_ALL_LIST,result).apply()
    }

    override fun clearAllList() {
        sharedPreferences.edit().putString(CURRENT_ALL_LIST,"").apply()
    }

    override fun getAllList(): List<MenuItem> {
        val listStr = sharedPreferences.getString(CURRENT_ALL_LIST,"")
        if(listStr == ""){
            return emptyList()
        } else {
            val type = object : TypeToken<List<MenuItem>>(){}.type
            val list = gson.fromJson<List<MenuItem>>(listStr,type)
            return list
        }
    }

    companion object{
        const val CURRENT_ALL_LIST = "CURRENT_ALL_LIST"
    }
}