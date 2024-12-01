package com.spase_y.vladfooddelivery.main.add_card.add_card.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.spase_y.vladfooddelivery.main.add_card.add_card.data.model.Card
import com.spase_y.vladfooddelivery.main.add_card.add_card.domain.api.CardRepository

class CardRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
):CardRepository {
    private val gson = Gson()
    private val cardsKey = "cards_key"

    override fun addCard(card: Card) {
        val cards = getAllCards().toMutableList()
        cards.add(card)
        saveCards(cards)
    }
    override fun removeCard(card: Card) {
        val cards = getAllCards().toMutableList()
        cards.remove(card)
        saveCards(cards)
    }
    override fun cleanAllCards() {
        saveCards(emptyList())
    }
    override fun getAllCards(): List<Card> {
        val json = sharedPreferences.getString(cardsKey, null)
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<Card>>() {}.type
            gson.fromJson(json, type)
        }
    }
    private fun saveCards(cards: List<Card>) {
        val json = gson.toJson(cards)
        sharedPreferences.edit()
            .putString(cardsKey, json)
            .apply()
    }
}