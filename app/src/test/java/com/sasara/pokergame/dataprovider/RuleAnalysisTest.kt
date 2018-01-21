package com.sasara.pokergame.dataprovider

import com.sasara.pokergame.data.entity.Card
import com.sasara.pokergame.common.constant.PokerHandType
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
    fun testCheckHighTypeCard_gotStraightFlush_typeIsStraightFlush() {

        val cardList = listOf<Card>(Card("ah"),
                Card("qh"),
                Card("jh"),
                Card("kh"),
                Card("10h"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.STRAIGHT_FLUSH.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.Ace
        }
    }

    @Test
    fun testCheckHighTypeCard_gotFourOfAKind_typeIsFourOfAKind() {

        val cardList = listOf<Card>(Card("jh"),
                Card("js"),
                Card("jc"),
                Card("jd"),
                Card("8h"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.FOUR_OF_A_KIND.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.Jack
        }
    }


    @Test
    fun testCheckHighTypeCard_gotFullHouse_typeIsFullHouse() {

        val cardList = listOf<Card>(Card("ah"),
                Card("as"),
                Card("js"),
                Card("js"),
                Card("jh"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.FULL_HOUSE.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.Jack
        }
    }

    @Test
    fun testCheckHighTypeCard_gotFlush_typeIsFlush() {

        val cardList = listOf<Card>(Card("2h"),
                Card("3h"),
                Card("9h"),
                Card("ah"),
                Card("kh"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.FLUSH.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.Ace
        }
    }

    @Test
    fun testCheckHighTypeCard_gotStraight_typeIsStraight() {

        val cardList = listOf<Card>(Card("2h"),
                Card("3s"),
                Card("9c"),
                Card("ac"),
                Card("kh"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.STRAIGHT.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.Ace
        }
    }

    @Test
    fun testCheckHighTypeCard_gotThreeOfAKind_typeIsThreeOfAKind() {

        val cardList = listOf<Card>(Card("jh"),
                Card("js"),
                Card("jc"),
                Card("2c"),
                Card("3h"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.THREE_OF_A_KIND.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.Jack
        }
    }

    @Test
    fun testCheckHighTypeCard_gotTwoPair_typeIsTwoPair() {

        val cardList = listOf<Card>(Card("ah"),
                Card("as"),
                Card("9c"),
                Card("9s"),
                Card("3h"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.TWO_PAIRS.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.Ace
        }
    }

    @Test
    fun testCheckHighTypeCard_gotOnePair_typeIsPair() {

        val cardList = listOf<Card>(Card("ah"),
                Card("as"),
                Card("8c"),
                Card("2c"),
                Card("3h"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.PAIR.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.Ace
        }
    }

    @Test
    fun testCheckHighTypeCard_gotHighCard_typeIsHighCard() {

        val cardList = listOf<Card>(Card("2h"),
                Card("3h"),
                Card("9s"),
                Card("ks"),
                Card("8h"))

        val testObserver = ruleAnalysis.getAnalysisResult(cardList).test()
        testObserver.assertValue {
            it.type.value == PokerHandType.HIGH_CARD.value
            it.compareRanks[it.compareRanks.lastIndex] == Card.King
        }
    }
}