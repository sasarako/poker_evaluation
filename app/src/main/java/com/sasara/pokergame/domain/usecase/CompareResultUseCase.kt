package com.sasara.pokergame.domain.usecase

import com.sasara.pokergame.data.entity.CompareResult
import com.sasara.pokergame.extension.toFullName
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Created by sasara on 9/20/2017 AD.
 */

val P1_WIN = "Somchai wins. with"
val P2_WIN = "Somsak wins. with"
val TIE = "Tie"

open class CompareResultUseCase(private val cardAnalysisUseCase1: CardAnalysisUseCase,
                           private val cardAnalysisUseCase2: CardAnalysisUseCase)
    : ObservableUseCase<CompareResult> {

    override fun toObservable(): Observable<CompareResult> {

        /**
         * ZipWith operation
         * Waiting 2 Observables are finish onNext().
         */

        return cardAnalysisUseCase1.toObservable().zipWith(cardAnalysisUseCase2.toObservable(), BiFunction { t1, t2 ->

            if (t1.type.value > t2.type.value) {
                //first player win with higher type
                CompareResult(CompareResult.P1_WIN, "$P1_WIN ${t1.type.showName}")
            } else if (t2.type.value > t1.type.value) {
                //second player win with higher type
                CompareResult(CompareResult.P2_WIN, "$P2_WIN ${t2.type.showName}")
            } else {
                //Types are equal
                if (t1.compareRanks.size != t2.compareRanks.size) {
                    //Card invalid Tie as default
                    CompareResult(CompareResult.TIE, TIE)
                }

                //Compare secondary rank
                //Sample 4S 5S 4C 5C 9H = 9,4,5 (Compare start by last index to first index)
                t1.compareRanks.reversed().forEachIndexed { index: Int, value: Int ->
                    val value1 = value
                    val value2 = t2.compareRanks.reversed()[index]

                    if (value1 > value2) {
                        return@BiFunction CompareResult(CompareResult.P1_WIN,
                                "$P1_WIN ${t1.type.showName}: ${value1.toFullName()}")
                    } else if (value2 > value1) {
                        return@BiFunction CompareResult(CompareResult.P2_WIN,
                                "$P2_WIN ${t2.type.showName}: ${value2.toFullName()}")
                    }
                }
                //Equal on every compare list
                CompareResult(CompareResult.TIE, TIE)
            }

        })

    }
}