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
        val email = sharedPreferences.getString("user_email", null)
        return !email.isNullOrEmpty()
    }

    fun saveUserEmail(email: String) {
        sharedPreferences.edit().putString("user_email", email).apply()
    }

    fun clearUserData() {
        sharedPreferences.edit().remove("user_email").apply()
    }
}
