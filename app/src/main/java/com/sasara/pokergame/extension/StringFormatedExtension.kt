package com.sasara.pokergame.extension

import com.sasara.pokergame.data.entity.CompareResult

/**
 * Created by sasara on 22/1/2018 AD.
 */

val P1_NAME = "Somchai"
val P2_NAME = "Somsak"
val WIN_WITH = "wins. with"
val TIE = "Tie"

fun String.pokerTypeToCompareResult(winner: Int, secondaryHigh: String = ""): CompareResult {

    return when (winner) {
        CompareResult.P1_WIN -> CompareResult(result = CompareResult.P1_WIN,
                resultMsg = if (secondaryHigh.isEmpty()) "$P1_NAME $WIN_WITH $this"  //Show only poker type
                            else "$P1_NAME $WIN_WITH $this: $secondaryHigh")         //Show poker type and high card

        CompareResult.P2_WIN -> CompareResult(result = CompareResult.P2_WIN,
                resultMsg = if (secondaryHigh.isEmpty()) "$P2_NAME $WIN_WITH $this"  //Show only poker type
                            else "$P2_NAME $WIN_WITH $this: $secondaryHigh")         //Show poker type and high card

        else -> CompareResult(result = CompareResult.TIE,
                resultMsg = TIE)
    }
}