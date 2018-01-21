package com.sasara.pokergame.data

import com.sasara.pokergame.hand.PokerHandType

/**
 * Created by sasara on 19/1/2018 AD.
 */
class OnHandResult(val type: PokerHandType,
                   val fiveSortedOnHandCards: List<Card>,
                   val compareRanks: List<Int>) {

}