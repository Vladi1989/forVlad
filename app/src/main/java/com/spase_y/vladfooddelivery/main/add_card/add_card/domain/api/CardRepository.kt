package com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api

import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card

interface CardRepository {
    fun addCard(card: Card)
    fun removeCard(card: Card)
    fun cleanAllCards()
    fun getAllCards():List<Card>
}