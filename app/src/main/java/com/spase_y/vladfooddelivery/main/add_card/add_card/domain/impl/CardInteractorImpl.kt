package com.spase_y.vladfooddelivery.main.add_card.add_card.domain.impl

import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardInteractor
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardRepository

class CardInteractorImpl(
    private val cardRepository: CardRepository
) : CardInteractor {

    override fun addCard(card: Card) {
        cardRepository.addCard(card)
    }

    override fun removeCard(card: Card) {
        cardRepository.removeCard(card)
    }

    override fun cleanAllCards() {
        cardRepository.cleanAllCards()
    }

    override fun getAllCards(): List<Card> {
        return cardRepository.getAllCards()
    }
}