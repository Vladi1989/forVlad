package com.spase_y.vladfooddelivery.main.add_card.add_card.data

import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardRepository

class CardRepositoryImpl:CardRepository {

    private val cardList = mutableListOf<Card>()

    override fun addCard(card: Card) {
        cardList.add(card)
    }

    override fun removeCard(card: Card) {
        cardList.remove(card)
    }

    override fun cleanAllCards() {
        cardList.clear()
    }

    override fun getAllCards(): List<Card> {
        return cardList.toList()
    }

}