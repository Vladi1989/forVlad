package com.spase_y.vladfooddelivery.loading.login.ui.model

interface PhoneNumberScreenState {
    object Loading: PhoneNumberScreenState
    class Error(val message:String): PhoneNumberScreenState
    class Result(val result: String) : PhoneNumberScreenState
    object CanGoNext: PhoneNumberScreenState
    object CantGoNext: PhoneNumberScreenState
}