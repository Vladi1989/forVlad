package com.spase_y.vladfooddelivery.loading.get_code.ui.view_model

import androidx.lifecycle.MutableLiveData
import com.spase_y.vladfooddelivery.loading.get_code.ui.model.GetCodeScreenState

class GetCodeViewModel {
    private var isValidCode = false

    val screenStateLD = MutableLiveData<GetCodeScreenState>()

    fun isValidCode(number1: String, number2: String, number3: String, number4: String) {
        isValidCode = (number1.isNotEmpty() && number2.isNotEmpty() && number3.isNotEmpty() && number4.isNotEmpty())
        emit()
    }
    private fun emit(){
        if (isValidCode){
            screenStateLD.postValue(GetCodeScreenState.CanGoNext)
        } else {
            screenStateLD.postValue(GetCodeScreenState.CantGoNext)
        }
    }
}