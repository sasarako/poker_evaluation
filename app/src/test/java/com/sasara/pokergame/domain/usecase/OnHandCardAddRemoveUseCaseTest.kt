package com.sasara.pokergame.domain.usecase

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.sasara.pokergame.data.entity.Card
import com.sasara.pokergame.data.datasource.CardProviderInterface
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by sasara on 21/1/2018 AD.
 */
class OnHandCardAddRemoveUseCaseTest {
    private lateinit var onHandCardAddRemoveUseCase: OnHandCardAddRemoveUseCase
    private val cardProvider: CardProviderInterface<Card> = mock()

    //Simulate data
    private val emptyCardList: List<Card> = listOf()

    private val fewCardsList: List<Card> = listOf(Card("JC"),
            Card("QC"),
            Card("KC"))

    private val fullCardsList: List<Card> = listOf(Card("2C"),
            Card("3C"),
            Card("4C"),
            Card("5C"),
            Card("6C"))

    @Before
    fun setup() {
        onHandCardAddRemoveUseCase = CardAddRemoveUseCaseImpl(cardProvider)
    }

    @Test
    fun testAddingCards_5CardsWith5Slots_ableToAdd() {

        doReturn(emptyCardList).whenever(cardProvider).getAllCards()
        val addingResult = onHandCardAddRemoveUseCase.updateOnHandCard(denotedString = "2H 3D 5S 9C KD")
        //success to add
        Assert.assertTrue(addingResult)

    }

    @Test
    fun testAddingCards_5CardsWrongFormatWith5Slot_failToAdd() {

        doReturn(emptyCardList).whenever(cardProvider).getAllCards()
        val addingResult = onHandCardAddRemoveUseCase.updateOnHandCard(denotedString = "ZH YD XS 9C KD")
        //Fail to add
        Assert.assertFalse(addingResult)

    }

    @Test
    fun testAddingCards_2CardsWith2Slots_ableToAdd() {

        doReturn(fewCardsList).whenever(cardProvider).getAllCards()
        val addingResult = onHandCardAddRemoveUseCase.updateOnHandCard(denotedString = "2H 3D")

        //Fail to add
        Assert.assertTrue(addingResult)
    }

    @Test
    fun testAddingCards_5CardsWithoutSlot_failToAdd() {

        doReturn(fullCardsList).whenever(cardProvider).getAllCards()
        val addingResult = onHandCardAddRemoveUseCase.updateOnHandCard(denotedString = "2H 3D 5S 9C KD")
        //Fail to add
        Assert.assertFalse(addingResult)

    }

}