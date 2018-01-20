package com.sasara.pokergame.usecase

import android.util.Log
import com.sasara.pokergame.data.Card
import com.sasara.pokergame.dataprovider.datasource.CardProviderInterface

/**
 * Created by sasara on 20/1/2018 AD.
 */

interface GetOnHandCardsUseCase {
    fun getOnHandCards(): List<Card>
    fun getOnHandAllCardsDenoted(): String
}

class GetOnHandCardsUseCaseImpl(private val cardProvider: CardProviderInterface<Card>)
    : GetOnHandCardsUseCase {
    override fun getOnHandAllCardsDenoted(): String {
        var allCardsConcat = ""
        getOnHandCards().forEach {
            allCardsConcat += it.getShortName()
        }
        return allCardsConcat
    }

    override fun getOnHandCards(): List<Card> {
        return cardProvider.getAllCards()
    }
}