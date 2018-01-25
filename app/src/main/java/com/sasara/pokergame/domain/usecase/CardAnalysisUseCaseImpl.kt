package com.sasara.pokergame.domain.usecase

import com.sasara.pokergame.data.datasource.CardProviderInterface
import com.sasara.pokergame.data.entity.Card
import com.sasara.pokergame.data.entity.OnHandResult
import com.sasara.pokergame.dataprovider.ResultInterface
import io.reactivex.Observable

/**
 * Created by sasara on 20/1/2018 AD.
 */

interface CardAnalysisUseCase {
    fun getOnHandResultObservable(): Observable<OnHandResult>
}

open class CardAnalysisUseCaseImpl(private val ruleAnalysis: ResultInterface<OnHandResult>,
                                   private val cardProvider: CardProviderInterface<Card>)
    : CardAnalysisUseCase {

    override fun getOnHandResultObservable(): Observable<OnHandResult> {

        return ruleAnalysis.getAnalysisResult(cardProvider.getAllCards())
    }

}
