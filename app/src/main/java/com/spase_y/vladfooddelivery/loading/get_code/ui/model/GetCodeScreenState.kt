package com.spase_y.vladfooddelivery.loading.get_code.ui.model


interface GetCodeScreenState {
    object Loading: GetCodeScreenState
    object CanGoNext: GetCodeScreenState
    object CantGoNext: GetCodeScreenState
}