package com.spase_y.vladfooddelivery.main.add_card.add_card.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardInteractor
import com.spase_y.vladfooddelivery.main.add_card.add_card.ui.model.CardScreenState

class CardViewModel(
    private val cardInteractor: CardInteractor
) : ViewModel() {

    private val _cardsLd = MutableLiveData<CardScreenState>()
    val cardsLd: LiveData<CardScreenState> get() = _cardsLd

    fun loadCards() {
        _cardsLd.value = CardScreenState.Loading
        try {
            val list = cardInteractor.getAllCards()
            _cardsLd.value = if (list.isNotEmpty()) {
                CardScreenState.Success(list)
            } else {
                CardScreenState.Error("Список карт пуст")
            }
        } catch (e: Exception) {
            _cardsLd.value = CardScreenState.Error("Ошибка загрузки карт: ${e.message}")
        }
    }

    fun addCard(card: Card) {
        try {
            cardInteractor.addCard(card)
            loadCards()
        } catch (e: Exception) {
            _cardsLd.value = CardScreenState.Error("Ошибка добавления карты: ${e.message}")
        }
    }

    fun removeCard(card: Card) {
        try {
            cardInteractor.removeCard(card)
            loadCards()
        } catch (e: Exception) {
            _cardsLd.value = CardScreenState.Error("Ошибка удаления карты: ${e.message}")
        }
    }

    fun clearAllCards() {
        try {
            cardInteractor.cleanAllCards()
            loadCards()
        } catch (e: Exception) {
            _cardsLd.value = CardScreenState.Error("Ошибка очистки списка карт: ${e.message}")
        }
    }
}