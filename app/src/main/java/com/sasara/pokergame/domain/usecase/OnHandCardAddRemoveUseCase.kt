package com.sasara.pokergame.domain.usecase

import com.sasara.pokergame.data.datasource.CardProviderInterface
import com.sasara.pokergame.data.entity.Card

/**
 * Created by sasara on 20/1/2018 AD.
 * UnitTest on OnHandCardAddRemoveUseCaseTest
 */

interface OnHandCardAddRemoveUseCase {
    fun updateOnHandCard(denotedString: String): Boolean
    fun removeAllCards()
    fun getOnHandCards(): List<Card>
    fun getOnHandAllCardsDenoted(): String
}

class CardAddRemoveUseCaseImpl(private val cardProvider: CardProviderInterface<Card>)
    : OnHandCardAddRemoveUseCase {

    override fun updateOnHandCard(denotedString: String): Boolean {

        denotedString.replace(" ", "").trim().
                toUpperCase().foldIndexed("", { index: Int, total, next ->
            if (index % 2 == 0) {
                //First index should be value of card (1,2,3,..,A)
                if ("23456789TJQKA".contains(next)) {
                    "$total$next"
                } else {
                    return false
                }
            } else {
                if ("CDHS".contains(next)) {
                    //Add " " to separate each card
                    "$total$next "
                } else {
                    return false
                }
            }
        }).trim().split(" ").forEach { eachCardString: String ->
            if (cardProvider.getAllCards().size >= 5) {
                return false
            }
            cardProvider.addCard(Card(eachCardString))
        }

        return true
    }

    override fun getOnHandAllCardsDenoted(): String {

        return getOnHandCards().fold("", { total, next ->
            "$total ${next.getShortName()}"
        }).trim()
    }

    override fun getOnHandCards(): List<Card> {

        return cardProvider.getAllCards()
    }

    override fun removeAllCards() {
        return cardProvider.clearAllCards()
    }
}