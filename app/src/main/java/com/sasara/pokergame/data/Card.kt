package com.sasara.pokergame.data

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
            "T" -> 10
            "J" -> 11
            "Q" -> 12
            "K" -> 13
            "A" -> 14

            else -> 0
        }
    }


}