package com.spase_y.vladfooddelivery.loading

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

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
