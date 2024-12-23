package com.spase_y.vladfooddelivery.main.details.domain.impl

import com.spase_y.vladfooddelivery.main.details.domain.api.DetailsInteractor
import com.spase_y.vladfooddelivery.main.details.domain.api.DetailsRepository

class DetailsInteractorImpl(private val repository: DetailsRepository): DetailsInteractor {
    override fun saveToRecommend(itemId: String) {
        repository.saveItemId(itemId)
    }

    override fun getRecommendList(): List<String> {
        return repository.getItemIds()
    }
}