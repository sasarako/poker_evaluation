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
    fun getAnalysisResult(cardList: List<Card>): Observable<T>
}

class RuleAnalysis() : ResultInterface<OnHandResult> {

    private var fiveSortedCards: List<Card> = mutableListOf()
    private var groupedAndSortedCards: List<Pair<Int, List<Card>>> = mutableListOf()


    override fun getAnalysisResult(cardList: List<Card>): Observable<OnHandResult> {
        fiveSortedCards = cardList.sortedBy { it.getRank() }
        //TODO comment
        groupedAndSortedCards = fiveSortedCards.groupBy({ it.getRank() }).toSortedMap().toList().
                sortedWith(compareBy({ it.second.size }, { it.first }))

        val compareRanks = groupedAndSortedCards.map { it.first }.map { it }

        val type = when {
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

        return Observable.just(OnHandResult(type = type,
                fiveSortedOnHandCards = fiveSortedCards,
                compareRanks = compareRanks))

    }


    private fun isStraightFlush(): Boolean {
        return isFlush() && isStraight()
    }

    private fun isFlush(): Boolean {
        if (fiveSortedCards.size < 5) {
            return false
        }
        return fiveSortedCards.all { it.getSuit() == fiveSortedCards[0].getSuit() }
    }

    private fun isStraight(): Boolean {

        val fiveSortedCopied = fiveSortedCards.sortedBy {
            it.getRank()
        }.toMutableList()

        if (fiveSortedCopied.isEmpty()) {
            return false
        }

        var tempCard = fiveSortedCopied[0].getRank()
        Log.d("koko", "tempCard $tempCard")

        fiveSortedCopied.removeAt(0)
        fiveSortedCopied.forEach {
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