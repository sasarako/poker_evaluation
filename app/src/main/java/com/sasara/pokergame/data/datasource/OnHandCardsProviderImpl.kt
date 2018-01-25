package com.sasara.pokergame.data.datasource

import com.sasara.pokergame.data.entity.Card

/**
 * Created by sasara on 19/1/2018 AD.
 */

interface CardProviderInterface<T> {
    fun getAllCards(): List<T>
    fun addCard(item: T)
    fun clearAllCards()
}

class OnHandCardsProviderImpl : CardProviderInterface<Card> {

    private var cardList = mutableListOf<Card>()

    override fun addCard(card: Card) {
        cardList.add(card)
    }

    override fun getAllCards(): List<Card> {
        return cardList
    }

    override fun clearAllCards() {
        cardList.clear()
    }
}