package com.spase_y.vladfooddelivery.loading.get_code.ui.view_model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spase_y.vladfooddelivery.loading.get_code.ui.model.GetCodeScreenState

class GetCodeViewModel(
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    private val _screenStateLD = MutableLiveData<GetCodeScreenState>()
    val screenStateLd: LiveData<GetCodeScreenState> = _screenStateLD

    fun verifyCode(inputCode:String,correctCode: String, phoneNumber: String){
        if(inputCode == correctCode){
            savePhoneNumber(phoneNumber)
            _screenStateLD.postValue(GetCodeScreenState.CanGoNext)
        } else {
            _screenStateLD.postValue(GetCodeScreenState.CantGoNext)
        }
    }
    private fun savePhoneNumber(phoneNumber: String){
        sharedPreferences.edit()
            .putString("PHONE_NUMBER_KEY", phoneNumber)
            .apply()
    }
}