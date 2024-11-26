package com.spase_y.vladfooddelivery.root.login_status.domain.impl

import com.spase_y.vladfooddelivery.root.login_status.domain.api.LoginUserInteractor
import com.spase_y.vladfooddelivery.root.login_status.domain.api.LoginUserRepository

class LoginUserInteractorImpl(
    private val repository: LoginUserRepository
): LoginUserInteractor {
    override fun isUserLoggedIn(): LoginUserInteractor.UserStatus{
        val isUserLoggedIn = repository.isUserLoggedIn()
        if(isUserLoggedIn){
            return LoginUserInteractor.UserStatus.LOGIN
        } else {
            return LoginUserInteractor.UserStatus.NOT_LOGIN
        }
    }

    override fun saveUserData(userNumber: String) {
        repository.saveUserData(userNumber)
    }

    override fun logOutUse() {
        repository.logOutUse()
    }
}