package com.sasara.pokergame.hand

/**
 * Created by sasara on 19/1/2018 AD.
 */

enum class PokerHandType(val value: Int, val showName: String) {
    UNDEFINED(0, "undefined"),
    HIGH_CARD(1, "high card"),
    PAIR(2, "pair"),
    TWO_PAIRS(3, "two pairs"),
    THREE_OF_A_KIND(4, "three of a kind"),
    STRAIGHT(5, "straight"),
    FLUSH(6, "flush"),
    FULL_HOUSE(7, "full house"),
    FOUR_OF_A_KIND(8, "four of a kind"),
    STRAIGHT_FLUSH(9, "straight flush")
}
