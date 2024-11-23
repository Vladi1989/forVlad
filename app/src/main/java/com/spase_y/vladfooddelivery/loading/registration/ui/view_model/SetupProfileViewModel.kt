package com.spase_y.vladfooddelivery.loading.registration.ui.view_model

import androidx.lifecycle.MutableLiveData
import com.spase_y.vladfooddelivery.loading.registration.ui.model.SetupProfileScreenState


class SetupProfileViewModel {

    private var isValidNumber = false
    private var isValidName = false
    private var isValidDate = false
    private var isAcceptPolicy = false

    val screenStateLD = MutableLiveData<SetupProfileScreenState>()
    fun isValidNumber(number: String) {
        isValidNumber = number.length in 7..14
        emit()
    }

    fun isValidName(number: String) {
        isValidName = number.length >= 2
        emit()
    }
    fun isValidDate(number: String) {
        isValidDate = number.length == 10
        emit()
    }
    fun isAcceptPrivacy(boolean: Boolean) {
        isAcceptPolicy = boolean
        emit()
    }
    private fun emit() {
        if (isValidNumber && isValidDate && isValidName && isAcceptPolicy) {
            screenStateLD.postValue(SetupProfileScreenState.CanGoNext)
        } else {
            screenStateLD.postValue(SetupProfileScreenState.CantGoNext)
        }
    }
}