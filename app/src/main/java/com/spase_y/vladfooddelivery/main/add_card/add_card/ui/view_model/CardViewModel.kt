package com.spase_y.vladfooddelivery.main.add_card.add_card.ui.view_model

import androidx.lifecycle.MutableLiveData
import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardInteractor
import com.spase_y.vladfooddelivery.main.add_card.add_card.ui.model.CardScreenState

class CardViewModel(
    private val cardInteractor: CardInteractor
) {
    val cardsLd = MutableLiveData<CardScreenState>()

    fun loadCards(){
        cardsLd.postValue(CardScreenState.Loading)
        val list = cardInteractor.getAllCards()
        cardsLd.postValue(CardScreenState.Success(list))
    }
    fun addCards(card: Card){
        cardInteractor.addCard(card)
        loadCards()
    }
    fun removeCard(card: Card){
        cardInteractor.removeCard(card)
        loadCards()
    }
}

