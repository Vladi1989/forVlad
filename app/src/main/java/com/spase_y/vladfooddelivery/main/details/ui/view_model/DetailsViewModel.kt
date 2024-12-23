package com.spase_y.vladfooddelivery.main.details.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spase_y.vladfooddelivery.main.details.domain.api.DetailsInteractor

class DetailsViewModel(private val interactor: DetailsInteractor):ViewModel() {

    private val _recommendList = MutableLiveData<List<String>>()
    val recommendList: LiveData<List<String>> get() = _recommendList

    fun saveItemToRecommend(itemId: String){
        interactor.saveToRecommend(itemId)
    }
    fun loadRecommendList(){
        _recommendList.value = interactor.getRecommendList()
    }
}