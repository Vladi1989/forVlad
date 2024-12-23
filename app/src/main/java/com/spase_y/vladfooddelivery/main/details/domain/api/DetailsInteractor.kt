package com.spase_y.vladfooddelivery.main.details.domain.api

interface DetailsInteractor {
    fun saveToRecommend(itemId:String)
    fun getRecommendList():List<String>
}