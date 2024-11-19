package com.spase_y.vladfooddelivery.root.login_status.data

import android.content.SharedPreferences
import com.spase_y.vladfooddelivery.root.login_status.domain.api.LoginUserRepository

class LoginUserRepositoryImpl(private val sharedPreferences: SharedPreferences):
    LoginUserRepository {
    override fun isUserLoggedIn(): Boolean {
        val userPhoneNumber =  sharedPreferences.getString("USER_PHONE_NUMBER","")!!
        return userPhoneNumber.isNotEmpty()
    }

    override fun saveUserData(userNumber: String) {
        sharedPreferences.edit().putString(USER_PHONE_NUMBER,userNumber).apply()
    }

    override fun logOutUse() {
        sharedPreferences.edit().putString(USER_PHONE_NUMBER,"").apply()
    }
    private companion object{
        const val USER_PHONE_NUMBER = "USER_PHONE_NUMBER"
    }

}