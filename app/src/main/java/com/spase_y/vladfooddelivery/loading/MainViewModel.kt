package com.spase_y.vladfooddelivery.loading

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application):AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

    fun isUserLoggedIn(): Boolean {
        val email = sharedPreferences.getString("user_email",null)
        return !email.isNullOrEmpty()
    }
    fun saveUserEmail(email: String){
        sharedPreferences.edit().putString("user_email",email).apply()
    }
    fun clearUserData(){
        sharedPreferences.edit().remove("user_email").apply()
    }
}