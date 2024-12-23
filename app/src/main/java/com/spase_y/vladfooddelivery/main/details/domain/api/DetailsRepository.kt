package com.spase_y.vladfooddelivery.main.details.domain.api

interface DetailsRepository {
    fun saveItemId(itemId: String)
    fun getItemIds(): List<String>
}