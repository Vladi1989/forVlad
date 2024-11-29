package com.spase_y.vladfooddelivery.main.add_card.add_card.ui.model

import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card

interface CardScreenState {
    object Loading : CardScreenState
    class Success(val list: List<Card>): CardScreenState
    class Error(val message: String): CardScreenState
}