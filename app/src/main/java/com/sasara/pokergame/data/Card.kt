package com.sasara.pokergame.data

import android.util.Log

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

class Card(private val donoted: String) : CardProperty {

    override fun getShortName(): String {
        return donoted
    }

    override fun getLongName(): String {
        return "${getValue()} of ${getSuit()}"
    }

    override fun getValue(): String {
        return if (donoted.isNotEmpty()) donoted[0].toString() else ""
    }

    override fun getSuit(): String {
        return if (donoted.length >= 2) donoted[1].toString() else ""
    }

    override fun getRank(): Int {
        if ("123456789".contains(getValue())) {
            return getValue().toInt()
        }

        return when (getValue().toUpperCase()) {
            "J" -> 10
            "Q" -> 11
            "K" -> 12
            "A" -> 11

            else -> 0
        }
    }


}