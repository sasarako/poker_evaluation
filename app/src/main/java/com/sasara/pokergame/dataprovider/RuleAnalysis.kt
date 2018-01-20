package com.sasara.pokergame.dataprovider

import android.util.Log
import com.sasara.pokergame.data.Card
import com.sasara.pokergame.data.OnHandResult
import com.sasara.pokergame.hand.PokerHandType
import io.reactivex.Observable

/**
 * Created by sasara on 19/1/2018 AD.
 */

interface ResultInterface<T> {
    fun getResult(): Observable<T>
}

class RuleAnalysis() : ResultInterface<OnHandResult> {

    private var type: Int = 0
    private var fiveSortedCards: List<Card> = mutableListOf()
    private var groupedAndSortedCards: List<Pair<Int, List<Card>>> = mutableListOf()

    private val compareRanks = groupedAndSortedCards.map { it.first }.map { it }


    fun analysisCards(fiveCards: List<Card>) {
        fiveSortedCards = fiveCards.sortedBy { it.getRank() }
        //TODO comment
        groupedAndSortedCards = fiveSortedCards.groupBy({ it.getRank() }).toSortedMap().toList().
                sortedWith(compareBy({ it.second.size }, { it.first }))

        type = when {
            isStraightFlush() -> PokerHandType.STRAIGHT_FLUSH
            isFourOfAKind() -> PokerHandType.FOUR_OF_A_KIND
            isFullHouse() -> PokerHandType.FULL_HOUSE
            isFlush() -> PokerHandType.FLUSH
            isStraight() -> PokerHandType.STRAIGHT
            isThreeOfAKind() -> PokerHandType.THREE_OF_A_KIND
            isTwoPairs() -> PokerHandType.TWO_PAIRS
            isOnePair() -> PokerHandType.PAIR
            isHighCard() -> PokerHandType.HIGH_CARD
            else -> PokerHandType.Undefined
        }


    }

    override fun getResult(): Observable<OnHandResult> {
        return Observable.just(OnHandResult(type = type,
                fiveSortedOnHandCards = fiveSortedCards.sortedBy { it.getRank() },
                compareRanks = compareRanks))
    }


    private fun isStraightFlush(): Boolean {
        return isFlush() && isStraight()
    }

    private fun isFlush(): Boolean {
        return fiveSortedCards.all { it.getSuit() == fiveSortedCards[0].getSuit() }
    }

    private fun isStraight(): Boolean {

        val b = fiveSortedCards.sortedBy {
            it.getRank()
        }.toMutableList()

        var tempCard = b[0].getRank()
        Log.d("koko", "tempCard $tempCard")

        b.removeAt(0)
        b.forEach {
            Log.d("koko", "it ${it.getRank()}")
            if (it.getRank() != tempCard + 1) {
                return false
            }
            tempCard = it.getRank()
        }

        return true
    }

    private fun isFourOfAKind(): Boolean {
        return groupedAndSortedCards.size == 2 && groupedAndSortedCards[1].second.size == 4
    }

    private fun isFullHouse(): Boolean {
        return groupedAndSortedCards.size == 2 && groupedAndSortedCards[1].second.size == 3
    }

    private fun isThreeOfAKind(): Boolean {
        return groupedAndSortedCards.size == 3 && groupedAndSortedCards[2].second.size == 3
    }

    private fun isTwoPairs(): Boolean {
        return groupedAndSortedCards.size == 3 && groupedAndSortedCards[2].second.size == 2
    }

    private fun isOnePair(): Boolean {
        return groupedAndSortedCards.size == 4
    }

    private fun isHighCard(): Boolean {
        return groupedAndSortedCards.size == 5
    }
}