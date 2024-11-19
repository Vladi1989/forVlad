package com.spase_y.vladfooddelivery.root.ui.model

import com.spase_y.vladfooddelivery.root.login_status.domain.api.LoginUserInteractor


interface MainScreenState {
    object Loading: MainScreenState
    data class Result(val isUserLoggedIn: LoginUserInteractor.UserStatus): MainScreenState
}