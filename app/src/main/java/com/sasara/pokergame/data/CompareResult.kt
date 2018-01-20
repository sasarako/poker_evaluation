package com.sasara.pokergame.data

/**
 * Created by sasara on 19/1/2018 AD.
 */

class CompareResult(val result: Int, val resultMsg: String) {
    companion object {
        const val TIE = 0
        const val P1_WIN = 1
        const val P2_WIN = 2
    }
}