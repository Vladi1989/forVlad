package com.spase_y.vladfooddelivery.root.ui.view_model

import android.content.SharedPreferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper
import com.spase_y.vladfooddelivery.root.login_status.domain.api.LoginUserInteractor
import com.spase_y.vladfooddelivery.root.ui.model.MainScreenState

class MainViewModel(
    private val loginUserInteractor: LoginUserInteractor
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
        val isLoggedIn = loginUserInteractor.isUserLoggedIn()
        mainLd.postValue(MainScreenState.Result(isLoggedIn))
    }


    fun saveUserNumber() {
        loginUserInteractor.saveUserData(userNumber)
    }
    fun setNumber(value: String){
        userNumber = value
    }

    companion object {
        private var userNumber = ""
    }
}
