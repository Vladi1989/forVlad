package com.spase_y.vladfooddelivery.root.login_status.domain.api

interface LoginUserRepository {
    fun isUserLoggedIn(): Boolean

    fun saveUserData(userNumber: String)

    fun logOutUse()


}