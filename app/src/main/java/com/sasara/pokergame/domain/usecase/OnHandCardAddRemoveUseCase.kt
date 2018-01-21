package com.sasara.pokergame.domain.usecase

import com.sasara.pokergame.data.entity.Card
import com.sasara.pokergame.data.datasource.CardProviderInterface

/**
 * Created by sasara on 20/1/2018 AD.
 */

interface OnHandCardAddRemoveUseCase {
    fun isCanAddToHand(denotedString: String): Boolean
    fun removeAllCards()
    fun getOnHandCards(): List<Card>
    fun getOnHandAllCardsDenoted(): String
}

class CardAddRemoveUseCaseImpl(private val cardProvider: CardProviderInterface<Card>)
    : OnHandCardAddRemoveUseCase {

    override fun isCanAddToHand(denotedString: String): Boolean {
        var cardDenotedFormated = ""

        //Remove space if exist
        denotedString.replace(" ", "").trim().
                toUpperCase().
                forEachIndexed { index: Int, c: Char ->
                    if (index % 2 == 0) {
                        //First index should be value of card (1,2,3,..,A)
                        if ("23456789TJQKA".contains(c)) {
                            cardDenotedFormated += c
                        } else {
                            return false
                        }
                    } else {
                        if ("CDHS".contains(c)) {
                            //Add "," to separate each card
                            //Last index no need to add " "
                            cardDenotedFormated += if (index == denotedString.lastIndex) "$c" else "$c "
                        } else {
                            return false
                        }
                    }
                }

        cardDenotedFormated.trim().split(" ").forEach { eachCardString: String ->
            if (cardProvider.getAllCards().size >= 5) {
                return false
            }
            cardProvider.addCard(Card(eachCardString))
        }
        return true
    }

    override fun getOnHandAllCardsDenoted(): String {
        var allCardsConcat = ""
        getOnHandCards().forEach {
            allCardsConcat += "${it.getShortName()} "
        }
        return allCardsConcat.trim()
    }

    override fun getOnHandCards(): List<Card> {
        return cardProvider.getAllCards()
    }

    override fun removeAllCards() {
        cardProvider.clearAllCards()
    }
}