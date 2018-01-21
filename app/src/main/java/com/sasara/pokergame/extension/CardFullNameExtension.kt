package com.sasara.pokergame.extension

/**
 * Created by sasara on 21/1/2018 AD.
 */
fun Int.toFullName(): String {

    return when (this) {
        10 -> "10"
        11 -> "Jack"
        12 -> "Queen"
        13 -> "King"
        14 -> "Ace"
        else -> {
            //Value is 1-9 ,Represented by its value instead.
            this.toString()
        }
    }
}