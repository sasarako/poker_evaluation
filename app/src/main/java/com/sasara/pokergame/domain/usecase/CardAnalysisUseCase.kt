package com.sasara.pokergame.domain.usecase

import com.sasara.pokergame.data.entity.Card
import com.sasara.pokergame.data.entity.OnHandResult
import com.sasara.pokergame.dataprovider.RuleAnalysis
import com.sasara.pokergame.data.datasource.CardProviderInterface
import io.reactivex.Observable

/**
 * Created by sasara on 20/1/2018 AD.
 */

/**
 * Created by sasara on 9/20/2017 AD.
 */
open class CardAnalysisUseCase(private val ruleAnalysis: RuleAnalysis,
                               private val cardProvider: CardProviderInterface<Card>)
    : ObservableUseCase<OnHandResult> {

    override fun toObservable(): Observable<OnHandResult> {

        return ruleAnalysis.getAnalysisResult(cardProvider.getAllCards())
    }

}
