package com.spase_y.vladfooddelivery.loading

import android.content.SharedPreferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper

class MainViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val mainLd = MutableLiveData<MainScreenState?>()

    fun getMainLd():LiveData<MainScreenState?>{
        return mainLd
    }

    init {
        checkUserLoginStatus()
    }

    private fun checkUserLoginStatus(){
        mainLd.postValue(MainScreenState.Loading)
        Handler(Looper.getMainLooper()).postDelayed({
            val isLoggedIn = isUserLoggedIn()
            mainLd.postValue(MainScreenState.Result(isLoggedIn))
        },1000)
    }


    fun isUserLoggedIn(): Boolean {
        val number = sharedPreferences.getString("user_number", null)
        return !number.isNullOrEmpty()
    }

    fun saveUserNumber() {
        sharedPreferences.edit().putString("user_number", userNumber).apply()
    }
    fun setNumber(value: String){
        userNumber = value
    }

    companion object {
        private var userNumber = ""
    }

}
