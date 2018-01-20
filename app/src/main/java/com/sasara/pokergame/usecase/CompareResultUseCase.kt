package com.sasara.pokergame.usecase

import android.util.Log
import com.sasara.pokergame.data.CompareResult
import com.sasara.pokergame.dataprovider.repository.ObservableUseCase
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Created by sasara on 9/20/2017 AD.
 */
open class CompareResultUseCase(private val cardAnalysisUseCase1: CardAnalysisUseCase,
                                private val cardAnalysisUseCase2: CardAnalysisUseCase)
    : ObservableUseCase<CompareResult> {

    override fun toObservable(): Observable<CompareResult> {

        /**
         * ZipWith operation
         * Waiting 2 Observables onNext() matched one to one.
         */

        return cardAnalysisUseCase1.toObservable().zipWith(cardAnalysisUseCase2.toObservable(), BiFunction { t1, t2 ->

            if (t1.type > t2.type) {
                CompareResult(CompareResult.P1_WIN, "player 1 win with type = ${t1.type}")
            } else if (t2.type > t1.type) {
                CompareResult(CompareResult.P2_WIN, "player 2 win with type = ${t2.type}")
            } else {

                if (t1.compareRanks.size != t2.compareRanks.size) {
                    //Card invalid Tie as default
                    CompareResult(CompareResult.TIE, "tie")
                }

                t1.compareRanks.reversed().forEachIndexed { index: Int, value: Int ->
                    val value1 = value
                    val value2 = t2.compareRanks.reversed()[index]

                    if (value1 > value2) {
                        return@BiFunction CompareResult(CompareResult.P1_WIN, "player 1 win with type = ${t1.type} and $value1")
                    } else if (value2 > value1) {
                        return@BiFunction CompareResult(CompareResult.P1_WIN, "player 2 win with type = ${t1.type} and $value2")
                    }
                }
                //Equal on every compare list
                CompareResult(CompareResult.TIE, "tie")
            }

        })

    }
}