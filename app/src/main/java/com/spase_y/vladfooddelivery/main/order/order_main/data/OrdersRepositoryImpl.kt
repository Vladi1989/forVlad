package com.spase_y.vladfooddelivery.main.order.order_main.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.order.order_main.domain.api.OrdersRepository
import com.spase_y.vladfooddelivery.root.Constants.CURRENT_ALL_LIST

class OrdersRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
): OrdersRepository {
    override fun addItem(item: MenuItem) {
        val currentList = getAllList().toMutableList()

        // Ищем элемент с таким же названием.
        val existingItem = currentList.find { it.name == item.name }

        if (existingItem != null) {
            // Если элемент найден, увеличиваем количество.
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
            val index = currentList.indexOf(existingItem)
            currentList[index] = updatedItem
        } else {
            // Если элемента нет, добавляем новый.
            currentList.add(item)
        }

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

    override fun replaceItem(oldItem: MenuItem, newItem: MenuItem) {
        val currentList = getAllList().toMutableList()
        val index = currentList.indexOfFirst { it == oldItem }
        if (index != -1){
            currentList[index] = newItem
            val result = gson.toJson(currentList)
            sharedPreferences.edit().putString(CURRENT_ALL_LIST,result).apply()
        }
    }
}