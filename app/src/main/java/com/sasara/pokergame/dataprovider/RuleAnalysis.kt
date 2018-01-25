package com.sasara.pokergame.dataprovider

import com.sasara.pokergame.data.entity.Card
import com.sasara.pokergame.data.entity.OnHandResult
import com.sasara.pokergame.common.constant.PokerHandType
import io.reactivex.Observable

/**
 * Created by sasara on 19/1/2018 AD.
 * UnitTest on RuleAnalysisTest
 */

interface ResultInterface<T> {
    fun getAnalysisResult(cardList: List<Card>): Observable<T>
}

class RuleAnalysis : ResultInterface<OnHandResult> {

    override fun getAnalysisResult(cardList: List<Card>): Observable<OnHandResult> {
        val fiveSortedCards = cardList.sortedBy { it.getRank() }

        //Group by rank value and sorted them convert to List of pair<Rank,Size>
        //Sample 4S 5S 4C 5C 9H = ("9",size = 1),("4",size = 2) , ("5", size = 2)
        val groupedAndSortedCards = fiveSortedCards.groupBy({ it.getRank() }).toSortedMap().toList().
                sortedWith(compareBy({ it.second.size }, { it.first }))

        //Generate secondary compare value
        //Sample 4S 5S 4C 5C 9H = 9,4,5 (Compare start by last index to first index)
        val compareRanks = groupedAndSortedCards.map { it.first }.map { it }

        val type: PokerHandType =
                when {
                    isStraightFlush(fiveSortedCards) -> PokerHandType.STRAIGHT_FLUSH
                    isFourOfAKind(groupedAndSortedCards) -> PokerHandType.FOUR_OF_A_KIND
                    isFullHouse(groupedAndSortedCards) -> PokerHandType.FULL_HOUSE
                    isFlush(fiveSortedCards) -> PokerHandType.FLUSH
                    isStraight(fiveSortedCards) -> PokerHandType.STRAIGHT
                    isThreeOfAKind(groupedAndSortedCards) -> PokerHandType.THREE_OF_A_KIND
                    isTwoPairs(groupedAndSortedCards) -> PokerHandType.TWO_PAIRS
                    isOnePair(groupedAndSortedCards) -> PokerHandType.PAIR
                    isHighCard(groupedAndSortedCards) -> PokerHandType.HIGH_CARD
                    else -> PokerHandType.UNDEFINED
                }

        //Type is primary factor to compare
        //CompareRanks is secondary factor to compare
        return Observable.just(OnHandResult(type = type,
                compareRanks = compareRanks))
    }


    private fun isStraightFlush(fiveSortedCards: List<Card>): Boolean {
        return isFlush(fiveSortedCards) && isStraight(fiveSortedCards)
    }

    private fun isFlush(fiveSortedCards: List<Card>): Boolean {
        if (fiveSortedCards.size < 5) {
            return false
        }
        //All cards are same suit.
        return fiveSortedCards.all { it.getSuit() == fiveSortedCards[0].getSuit() }
    }

    private fun isStraight(fiveSortedCards: List<Card>): Boolean {

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

    private fun isFourOfAKind(groupedAndSortedCards: List<Pair<Int, List<Card>>>): Boolean {
        //Amount of grouped with rank
        //Same value = 4
        return groupedAndSortedCards.size == 2 && groupedAndSortedCards[1].second.size == 4
    }

    private fun isFullHouse(groupedAndSortedCards: List<Pair<Int, List<Card>>>): Boolean {
        //Amount of grouped with rank
        //Same primary value = 3 and size of grouping = 2 (with 1 pair same rank)
        return groupedAndSortedCards.size == 2 && groupedAndSortedCards[1].second.size == 3
    }

    private fun isThreeOfAKind(groupedAndSortedCards: List<Pair<Int, List<Card>>>): Boolean {
        //Amount of grouped with rank
        //Same primary value = 3 and size of grouping = 3 (without another same rank)
        return groupedAndSortedCards.size == 3 && groupedAndSortedCards[2].second.size == 3
    }

    private fun isTwoPairs(groupedAndSortedCards: List<Pair<Int, List<Card>>>): Boolean {
        //Amount of grouped with rank
        //Same primary value = 2 and size of grouping = 2
        return groupedAndSortedCards.size == 3 && groupedAndSortedCards[2].second.size == 2
    }

    private fun isOnePair(groupedAndSortedCards: List<Pair<Int, List<Card>>>): Boolean {
        //Amount of grouped with rank
        //Same primary value = 2 and size of grouping = 4 (without another same rank)
        return groupedAndSortedCards.size == 4
    }

    private fun isHighCard(groupedAndSortedCards: List<Pair<Int, List<Card>>>): Boolean {
        return groupedAndSortedCards.size == 5
    }
}