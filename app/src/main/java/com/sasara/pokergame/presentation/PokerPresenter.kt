package com.sasara.pokergame.presentation

import com.sasara.pokergame.common.ReadWriteWeakRefDelegate
import com.sasara.pokergame.data.entity.CompareResult
import com.sasara.pokergame.domain.usecase.CompareResultUseCase
import com.sasara.pokergame.domain.usecase.OnHandCardAddRemoveUseCase
import com.sasara.pokergame.extension.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by sasara on 17/1/2018 AD.
 * UnitTest on PokerPresenterTest
 */
class PokerPresenter(view: PokerContract.View,
                     private val p1OnHandCardAddRemoveUseCase: OnHandCardAddRemoveUseCase,
                     private val p2OnHandCardAddRemoveUseCase: OnHandCardAddRemoveUseCase,
                     private val compareResultUseCase: CompareResultUseCase) : PokerContract.presenter {

    private var view by ReadWriteWeakRefDelegate<PokerPresenter,
            PokerContract.View>(view)
    private var disposeBag = CompositeDisposable()

    override fun addFirstPlayerCards(denotedCard: String) {

        if (!p1OnHandCardAddRemoveUseCase.updateOnHandCard(denotedCard)) {
            view?.showErrorAddingFail()
        }
        view?.updateFirstPlayerCards(p1OnHandCardAddRemoveUseCase.getOnHandAllCardsDenoted())
    }

    override fun addSecondPlayerCards(denotedCard: String) {
        if (!p2OnHandCardAddRemoveUseCase.updateOnHandCard(denotedCard)) {
            view?.showErrorAddingFail()
        }
        view?.updateSecondPlayerCards(p2OnHandCardAddRemoveUseCase.getOnHandAllCardsDenoted())
    }

    override fun analysisResult() {

        if (p1OnHandCardAddRemoveUseCase.getOnHandCards().size != 5) {
            //plural
            view?.showErrorP1NotEnoughCard()
            return
        }
        if (p2OnHandCardAddRemoveUseCase.getOnHandCards().size != 5) {
            view?.showErrorP2NotEnoughCard()
            return
        }

        compareResultUseCase.getCompareResultObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ compareResult: CompareResult ->
                    view?.showGameResult(compareResult.resultMsg)
                }, { t: Throwable ->

                }).addTo(disposeBag)
    }

    override fun clearP1Cards() {
        p1OnHandCardAddRemoveUseCase.removeAllCards()
        view?.clearP1View()
    }

    override fun clearP2Cards() {
        p2OnHandCardAddRemoveUseCase.removeAllCards()
        view?.clearP2View()
    }


    override fun startNewGame() {
        p1OnHandCardAddRemoveUseCase.removeAllCards()
        p2OnHandCardAddRemoveUseCase.removeAllCards()
        view?.clearAllView()
    }

    override fun cleanUp() {
        view = null
        disposeBag.clear()
    }

}