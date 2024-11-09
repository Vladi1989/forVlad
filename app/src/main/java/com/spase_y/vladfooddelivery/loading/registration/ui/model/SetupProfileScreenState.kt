package com.spase_y.vladfooddelivery.loading.registration.ui.model

interface SetupProfileScreenState {
    object Loading: SetupProfileScreenState
    object CanGoNext: SetupProfileScreenState
    object CantGoNext: SetupProfileScreenState
}