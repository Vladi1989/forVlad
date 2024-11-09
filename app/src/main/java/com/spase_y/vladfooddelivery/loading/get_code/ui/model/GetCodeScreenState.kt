package com.spase_y.vladfooddelivery.loading.get_code.ui.model

import com.spase_y.vladfooddelivery.loading.login.ui.model.PhoneNumberScreenState

interface GetCodeScreenState {
    object Loading: GetCodeScreenState
    object CanGoNext: GetCodeScreenState
    object CantGoNext: GetCodeScreenState
}