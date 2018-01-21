package com.sasara.pokergame.dataprovider

import com.sasara.pokergame.data.Card
import com.sasara.pokergame.hand.PokerHandType
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test


/**
 * Created by sasara on 20/1/2018 AD.
 */
class RuleAnalysisTest {

    private val ruleAnalysis: RuleAnalysis = RuleAnalysis()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun teardown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun testCheckHighTypeCard_flushAsHighest_typeShouldBeFlush() {

        val cardList = listOf<Card>(Card("2h"),
                Card("3h"),
                Card("9h"),
                Card("ah"),
                Card("kh"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.FLUSH.value
            it.compareRanks[it.compareRanks.lastIndex] == 14
        }

    }

}