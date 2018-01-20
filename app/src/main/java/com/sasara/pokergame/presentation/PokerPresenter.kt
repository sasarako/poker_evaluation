package com.sasara.pokergame.presentation

import android.util.Log
import com.sasara.pokergame.common.ReadWriteWeakRefDelegate
import com.sasara.pokergame.data.CompareResult
import com.sasara.pokergame.usecase.CompareResultUseCase
import com.sasara.pokergame.usecase.OnHandCardAddRemoveUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by sasara on 17/1/2018 AD.
 */
class PokerPresenter(view: PokerContract.View,
                     private val p1OnHandCardAddRemoveUseCase: OnHandCardAddRemoveUseCase,
                     private val p2OnHandCardAddRemoveUseCase: OnHandCardAddRemoveUseCase,
                     private val compareResultUseCase: CompareResultUseCase) : PokerContract.presenter {


    private var view by ReadWriteWeakRefDelegate<PokerPresenter,
            PokerContract.View>(view)

    override fun addFirstPlayerCards(denotedCard: String) {

        if (!p1OnHandCardAddRemoveUseCase.isCanAddToHand(denotedCard)) {
            view?.showErrorMsg("Adding fail")
        }
        view?.updateFirstPlayerCards(p1OnHandCardAddRemoveUseCase.getOnHandAllCardsDenoted())
    }

    override fun addSecondPlayerCards(denotedCard: String) {
        if (!p2OnHandCardAddRemoveUseCase.isCanAddToHand(denotedCard)) {
            view?.showErrorMsg("Adding fail")
        }
        view?.updateSecondPlayerCards(p2OnHandCardAddRemoveUseCase.getOnHandAllCardsDenoted())
    }

    override fun analysisResult() {

        compareResultUseCase.toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ compareResult: CompareResult ->
                    Log.d("koko", "compareResult ${compareResult}")
                    view?.showGameResult("${compareResult.result} with ${compareResult.resultMsg}")
                }, { t: Throwable ->
                    Log.d("koko", "throw ${t.message}")
                })
    }

    override fun startNewGame() {
        p1OnHandCardAddRemoveUseCase.removeAllCards()
        p2OnHandCardAddRemoveUseCase.removeAllCards()
        view?.clearAllView()
    }

}