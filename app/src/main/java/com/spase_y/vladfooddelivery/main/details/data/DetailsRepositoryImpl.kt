package com.spase_y.vladfooddelivery.main.details.data

import com.spase_y.vladfooddelivery.main.details.domain.api.DetailsRepository

class DetailsRepositoryImpl: DetailsRepository {
    private val idList = mutableListOf<String>()
    override fun saveItemId(itemId: String) {
        if(!idList.contains(itemId)){
            idList.add(itemId)
        }
    }

    override fun getItemIds(): List<String> {
        return idList.toList()
    }
}