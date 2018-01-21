package com.sasara.pokergame.dataprovider

import com.sasara.pokergame.data.entity.Card
import com.sasara.pokergame.data.entity.OnHandResult
import com.sasara.pokergame.common.constant.PokerHandType
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

        //Group by rank value and sorted them convert to List of pair<Rank,Size>
        //Sample 4S 5S 4C 5C 9H = ("9",size = 1),("4",size = 2) , ("5", size = 2)
        groupedAndSortedCards = fiveSortedCards.groupBy({ it.getRank() }).toSortedMap().toList().
                sortedWith(compareBy({ it.second.size }, { it.first }))

        //Generate secondary compare value
        //Sample 4S 5S 4C 5C 9H = 9,4,5 (Compare start by last index to first index)
        val compareRanks = groupedAndSortedCards.map { it.first }.map { it }


        val type: PokerHandType =
                when {
                    isStraightFlush() -> PokerHandType.STRAIGHT_FLUSH
                    isFourOfAKind() -> PokerHandType.FOUR_OF_A_KIND
                    isFullHouse() -> PokerHandType.FULL_HOUSE
                    isFlush() -> PokerHandType.FLUSH
                    isStraight() -> PokerHandType.STRAIGHT
                    isThreeOfAKind() -> PokerHandType.THREE_OF_A_KIND
                    isTwoPairs() -> PokerHandType.TWO_PAIRS
                    isOnePair() -> PokerHandType.PAIR
                    isHighCard() -> PokerHandType.HIGH_CARD
                    else -> PokerHandType.UNDEFINED
                }

        //Type is primary factor to compare
        //CompareRanks is secondary factor to compare
        return Observable.just(OnHandResult(type = type,
                compareRanks = compareRanks))

    }


    private fun isStraightFlush(): Boolean {
        return isFlush() && isStraight()
    }

    private fun isFlush(): Boolean {
        if (fiveSortedCards.size < 5) {
            return false
        }
        //All cards are same suit.
        return fiveSortedCards.all { it.getSuit() == fiveSortedCards[0].getSuit() }
    }

    private fun isStraight(): Boolean {

        val fiveSortedCopied = fiveSortedCards.sortedBy {
            it.getRank()
        }.toMutableList()

        if (fiveSortedCopied.size < 5) {
            return false
        }

        var tempCard = fiveSortedCopied[0].getRank()

        fiveSortedCopied.removeAt(0)
        fiveSortedCopied.forEach {
            if (it.getRank() != tempCard + 1) {
                return false
            }
            tempCard = it.getRank()
        }
        //For sorted 5 cards ,Next card value = current value +1
        return true
    }

    private fun isFourOfAKind(): Boolean {
        //Amount of grouped with rank
        //Same value = 4
        return groupedAndSortedCards.size == 2 && groupedAndSortedCards[1].second.size == 4
    }

    private fun isFullHouse(): Boolean {
        //Amount of grouped with rank
        //Same primary value = 3 and size of grouping = 2 (with 1 pair same rank)
        return groupedAndSortedCards.size == 2 && groupedAndSortedCards[1].second.size == 3
    }

    private fun isThreeOfAKind(): Boolean {
        //Amount of grouped with rank
        //Same primary value = 3 and size of grouping = 3 (without another same rank)
        return groupedAndSortedCards.size == 3 && groupedAndSortedCards[2].second.size == 3
    }

    private fun isTwoPairs(): Boolean {
        //Amount of grouped with rank
        //Same primary value = 2 and size of grouping = 2
        return groupedAndSortedCards.size == 3 && groupedAndSortedCards[2].second.size == 2
    }

    private fun isOnePair(): Boolean {
        //Amount of grouped with rank
        //Same primary value = 2 and size of grouping = 4 (without another same rank)
        return groupedAndSortedCards.size == 4
    }

    private fun isHighCard(): Boolean {
        return groupedAndSortedCards.size == 5
    }
}