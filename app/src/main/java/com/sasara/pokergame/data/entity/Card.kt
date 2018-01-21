package com.sasara.pokergame.data.entity

/**
 * Created by sasara on 18/1/2018 AD.
 */

interface CardProperty {

    fun getShortName(): String
    fun getLongName(): String
    fun getValue(): String
    fun getSuit(): String
    fun getRank(): Int
}

class Card(private val denoted: String) : CardProperty {

    companion object {
        //Rank const for above 1-9 value
        const val Ten = 10
        const val Jack = 11
        const val Queen = 12
        const val King = 13
        const val Ace = 14
    }

    override fun getShortName(): String {
        return denoted
    }

    override fun getLongName(): String {
        return "${getValue()} of ${getSuit()}"
    }

    override fun getValue(): String {
        return if (denoted.isNotEmpty()) denoted[0].toString() else ""
    }

    override fun getSuit(): String {
        return if (denoted.length >= 2) denoted[1].toString() else ""
    }

    override fun getRank(): Int {
        if ("123456789".contains(getValue())) {
            return getValue().toInt()
        }

        return when (getValue().toUpperCase()) {
            "T" -> Ten
            "J" -> Jack
            "Q" -> Queen
            "K" -> King
            "A" -> Ace
            else -> 0
        }
    }
}