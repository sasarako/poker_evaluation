package com.sasara.pokergame.presentation

import com.nhaarman.mockito_kotlin.*
import com.sasara.pokergame.data.entity.Card
import com.sasara.pokergame.data.entity.CompareResult
import com.sasara.pokergame.domain.usecase.CompareResultUseCase
import com.sasara.pokergame.domain.usecase.OnHandCardAddRemoveUseCase
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by sasara on 21/1/2018 AD.
 */

class PokerPresenterTest() {

    private var view: PokerContract.View = mock()
    lateinit var presenter: PokerPresenter

    private val p1OnHandCardAddRemoveUseCase: OnHandCardAddRemoveUseCase = mock()
    private val p2OnHandCardAddRemoveUseCase: OnHandCardAddRemoveUseCase = mock()
    private val compareResultUseCase: CompareResultUseCase = mock()

    //Success mock
    private val enoughCardList1 = listOf<Card>(Card("2H"),
            Card("3H"),
            Card("4H"),
            Card("5H"),
            Card("6H"))

    private val enoughCardList2 = listOf<Card>(Card("2S"),
            Card("3S"),
            Card("4S"),
            Card("5S"),
            Card("6S"))

    private val notEnoughCardList = listOf<Card>(Card("2C"),
            Card("3C"),
            Card("4C"),
            Card("5C"))

    private val compareResult = CompareResult(CompareResult.P1_WIN,
            "Somsak wins. with high card: Ace")

    //Error mock
    private val t: Throwable = Throwable("General Throw!!")
    private val e: Exception = Exception("General Exception!!")

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }

        presenter = PokerPresenter(view = view,
                p1OnHandCardAddRemoveUseCase = p1OnHandCardAddRemoveUseCase,
                p2OnHandCardAddRemoveUseCase = p2OnHandCardAddRemoveUseCase,
                compareResultUseCase = compareResultUseCase)
    }

    @After
    fun teardown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun laddFirstPlayerCards_success_updateFirstPlayerCards() {

        doReturn(true).whenever(p1OnHandCardAddRemoveUseCase).updateOnHandCard("2H 3D 5S 9C KD")
        doReturn("2H 3D 5S 9C KD").whenever(p1OnHandCardAddRemoveUseCase).getOnHandAllCardsDenoted()

        presenter.addFirstPlayerCards("2H 3D 5S 9C KD")

        inOrder(view) {
            verify(view, never()).showErrorAddingFail()

            verify(view).updateFirstPlayerCards("2H 3D 5S 9C KD")
        }
    }

    @Test
    fun laddFirstPlayerCards_fail_showAddingFail() {

        doReturn(false).whenever(p2OnHandCardAddRemoveUseCase).updateOnHandCard("2H 3D 5S 9C KD")
        doReturn("2H 3D 5S 9C KD").whenever(p2OnHandCardAddRemoveUseCase).getOnHandAllCardsDenoted()

        presenter.addSecondPlayerCards("2H 3D 5S 9C KD")

        inOrder(view) {
            verify(view).showErrorAddingFail()
            verify(view).updateSecondPlayerCards("2H 3D 5S 9C KD")
        }
    }

    @Test
    fun laddSecondPlayerCards_success_updateFirstPlayerCards() {

        doReturn(true).whenever(p2OnHandCardAddRemoveUseCase).updateOnHandCard("2H 3D 5S 9C KD")
        doReturn("2H 3D 5S 9C KD").whenever(p2OnHandCardAddRemoveUseCase).getOnHandAllCardsDenoted()

        presenter.addSecondPlayerCards("2H 3D 5S 9C KD")

        inOrder(view) {
            verify(view, never()).showErrorAddingFail()

            verify(view).updateSecondPlayerCards("2H 3D 5S 9C KD")
        }
    }

    @Test
    fun laddSecondPlayerCards_fail_showAddingFail() {

        doReturn(false).whenever(p1OnHandCardAddRemoveUseCase).updateOnHandCard("2H 3D 5S 9C KD")
        doReturn("2H 3D 5S 9C KD").whenever(p1OnHandCardAddRemoveUseCase).getOnHandAllCardsDenoted()

        presenter.addFirstPlayerCards("2H 3D 5S 9C KD")

        inOrder(view) {
            verify(view).showErrorAddingFail()
            verify(view).updateFirstPlayerCards("2H 3D 5S 9C KD")
        }
    }

    @Test
    fun analysisResult_sucess_showGameReult() {

        doReturn(enoughCardList1).whenever(p1OnHandCardAddRemoveUseCase).getOnHandCards()
        doReturn(enoughCardList2).whenever(p2OnHandCardAddRemoveUseCase).getOnHandCards()
        doReturn(Observable.just(compareResult)).whenever(compareResultUseCase).getCompareResultObservable()

        presenter.analysisResult()

        inOrder(view) {
            verify(view, never()).showErrorP1NotEnoughCard()
            verify(view, never()).showErrorP2NotEnoughCard()

            verify(view).showGameResult(compareResult.resultMsg)
        }
    }

    @Test
    fun analysisResult_failP1NotEnough_alertErrorP1() {

        doReturn(notEnoughCardList).whenever(p1OnHandCardAddRemoveUseCase).getOnHandCards()
        doReturn(enoughCardList2).whenever(p2OnHandCardAddRemoveUseCase).getOnHandCards()
        doReturn(Observable.just(compareResult)).whenever(compareResultUseCase).getCompareResultObservable()

        presenter.analysisResult()

        inOrder(view) {
            verify(view).showErrorP1NotEnoughCard()

            verify(view, never()).showErrorP2NotEnoughCard()
            verify(view, never()).showGameResult(compareResult.resultMsg)
        }
    }

    @Test
    fun analysisResult_failP2NotEnough_alertErrorP2() {

        doReturn(enoughCardList1).whenever(p1OnHandCardAddRemoveUseCase).getOnHandCards()
        doReturn(notEnoughCardList).whenever(p2OnHandCardAddRemoveUseCase).getOnHandCards()
        doReturn(Observable.just(compareResult)).whenever(compareResultUseCase).getCompareResultObservable()

        presenter.analysisResult()

        inOrder(view) {
            verify(view).showErrorP2NotEnoughCard()

            verify(view, never()).showErrorP1NotEnoughCard()
            verify(view, never()).showGameResult(compareResult.resultMsg)
        }
    }
}