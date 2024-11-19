package com.spase_y.vladfooddelivery.root.login_status.domain.api

interface LoginUserInteractor {
    fun isUserLoggedIn(): UserStatus

    fun saveUserData(userNumber: String)

    fun logOutUse()

    enum class UserStatus(){
        LOGIN,
        NOT_LOGIN
    }
}