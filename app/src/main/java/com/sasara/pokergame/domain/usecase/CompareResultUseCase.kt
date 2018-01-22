package com.sasara.pokergame.domain.usecase

import com.sasara.pokergame.data.entity.CompareResult
import com.sasara.pokergame.extension.pokerTypeToCompareResult
import com.sasara.pokergame.extension.toFullName
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Created by sasara on 9/20/2017 AD.
 * UnitTest on CompareResultUseCaseTest
 */

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
                //first player win with higher poker type
                t1.type.showName.pokerTypeToCompareResult(winner = CompareResult.P1_WIN)

            } else if (t2.type.value > t1.type.value) {
                //second player win with higher poker type
                t2.type.showName.pokerTypeToCompareResult(winner = CompareResult.P2_WIN)
            } else {
                //Poker types are equal
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
                        return@BiFunction t1.type.showName.pokerTypeToCompareResult(winner = CompareResult.P1_WIN,
                                                                                secondaryHigh = value1.toFullName())
                    } else if (value2 > value1) {
                        return@BiFunction t2.type.showName.pokerTypeToCompareResult(winner = CompareResult.P2_WIN,
                                                                                secondaryHigh = value2.toFullName())
                    }
                }
                //Equal on every compare list
                CompareResult(CompareResult.TIE, TIE)
            }

        })

    }
}