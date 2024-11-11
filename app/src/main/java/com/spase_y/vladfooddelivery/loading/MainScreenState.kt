package com.spase_y.vladfooddelivery.loading


interface MainScreenState {
    object Loading: MainScreenState
    data class Result(val isUserLoggedIn: Boolean):MainScreenState
}