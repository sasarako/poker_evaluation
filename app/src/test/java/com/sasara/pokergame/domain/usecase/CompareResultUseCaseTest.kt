package com.sasara.pokergame.domain.usecase

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.sasara.pokergame.data.entity.CompareResult
import com.sasara.pokergame.data.entity.OnHandResult
import com.sasara.pokergame.common.constant.PokerHandType
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

class CompareResultUseCaseTest {

    private lateinit var compareResultUseCaseTest: CompareResultUseCase
    private val cardAnalysisUseCase1: CardAnalysisUseCase = mock()
    private val cardAnalysisUseCase2: CardAnalysisUseCase = mock()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }

        compareResultUseCaseTest = CompareResultUseCase(cardAnalysisUseCase1, cardAnalysisUseCase2)
    }

    @After
    fun teardown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun testCompare2CardSets_p1WinsWithType_resultP1Win() {

        val p1OnHandResult = OnHandResult(type = PokerHandType.THREE_OF_A_KIND,
                compareRanks = listOf())
        val p2OnHandResult = OnHandResult(type = PokerHandType.TWO_PAIRS,
                compareRanks = listOf())

        doReturn(Observable.just(p1OnHandResult)).whenever(cardAnalysisUseCase1).toObservable()
        doReturn(Observable.just(p2OnHandResult)).whenever(cardAnalysisUseCase2).toObservable()

        val testObserver = compareResultUseCaseTest.toObservable().test()

        testObserver.assertValue { compareResult ->
            compareResult.result == CompareResult.P1_WIN
            compareResult.resultMsg == "Somchai wins. with three of a kind"
        }
    }

    @Test
    fun testCompare2CardSets_p2WinsWithType_resultP2Win() {

        val p1OnHandResult = OnHandResult(type = PokerHandType.FLUSH,
                compareRanks = listOf())
        val p2OnHandResult = OnHandResult(type = PokerHandType.FULL_HOUSE,
                compareRanks = listOf())

        doReturn(Observable.just(p1OnHandResult)).whenever(cardAnalysisUseCase1).toObservable()
        doReturn(Observable.just(p2OnHandResult)).whenever(cardAnalysisUseCase2).toObservable()

        val testObserver = compareResultUseCaseTest.toObservable().test()

        testObserver.assertValue { compareResult ->
            compareResult.result == CompareResult.P2_WIN
            compareResult.resultMsg == "Somsak wins. with full house"
        }
    }

    @Test
    fun testCompare2CardSets_p1WinsWithCompareRanks_resultP1Win() {

        //Simulate p1 cards = 5,5,5,2,6
        val p1OnHandResult = OnHandResult(type = PokerHandType.THREE_OF_A_KIND,
                compareRanks = listOf(2, 6, 5))
        //Simulate p2 cards = 3,3,3,2,6
        val p2OnHandResult = OnHandResult(type = PokerHandType.THREE_OF_A_KIND,
                compareRanks = listOf(2, 6, 3))

        doReturn(Observable.just(p1OnHandResult)).whenever(cardAnalysisUseCase1).toObservable()
        doReturn(Observable.just(p2OnHandResult)).whenever(cardAnalysisUseCase2).toObservable()

        val testObserver = compareResultUseCaseTest.toObservable().test()

        testObserver.assertValue { compareResult ->
            compareResult.result == CompareResult.P1_WIN
            compareResult.resultMsg == "Somchai wins. with three of a kind: 5"
        }
    }

    @Test
    fun testCompare2CardSets_p2WinsWithCompareRanks_resultP2Win() {

        //Simulate p1 cards = 3,4,5,6,7
        val p1OnHandResult = OnHandResult(type = PokerHandType.STRAIGHT,
                compareRanks = listOf(3, 4, 5, 6, 7))
        //Simulate p2 cards = 5,6,7,8,9
        val p2OnHandResult = OnHandResult(type = PokerHandType.STRAIGHT,
                compareRanks = listOf(5, 6, 7, 8, 9))

        doReturn(Observable.just(p1OnHandResult)).whenever(cardAnalysisUseCase1).toObservable()
        doReturn(Observable.just(p2OnHandResult)).whenever(cardAnalysisUseCase2).toObservable()

        val testObserver = compareResultUseCaseTest.toObservable().test()

        testObserver.assertValue { compareResult ->
            compareResult.result == CompareResult.P2_WIN
            compareResult.resultMsg == "Somsak wins. with straight: 9"
        }
    }

    @Test
    fun testCompare2CardSets_tieWithType_resultTie() {

        //Simulate p1 cards = 3,4,5,6,7 with Club suit
        val p1OnHandResult = OnHandResult(type = PokerHandType.STRAIGHT_FLUSH,
                compareRanks = listOf(3, 4, 5, 6, 7))
        //Simulate p2 cards = 3,4,5,6,7 with Diamonds suit
        val p2OnHandResult = OnHandResult(type = PokerHandType.STRAIGHT_FLUSH,
                compareRanks = listOf(3, 4, 5, 6, 7))

        doReturn(Observable.just(p1OnHandResult)).whenever(cardAnalysisUseCase1).toObservable()
        doReturn(Observable.just(p2OnHandResult)).whenever(cardAnalysisUseCase2).toObservable()

        val testObserver = compareResultUseCaseTest.toObservable().test()

        testObserver.assertValue { compareResult ->
            compareResult.result == CompareResult.TIE
            compareResult.resultMsg == "Tie"
        }
    }
}