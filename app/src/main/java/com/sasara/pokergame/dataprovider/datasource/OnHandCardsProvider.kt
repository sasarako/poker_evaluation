package com.sasara.pokergame.dataprovider.datasource

import com.sasara.pokergame.data.Card
import io.reactivex.Observable

/**
 * Created by sasara on 19/1/2018 AD.
 */

interface ICardProvider<T> {
    fun getFromLocal(): Observable<List<T>>
    fun updateLocal(item: T)
}

class OnHandCardsProvider() : ICardProvider<Card> {

    private var cardList = mutableListOf<Card>()


    override fun getFromLocal(): Observable<List<Card>> {
        return Observable.just(cardList)
    }

    override fun updateLocal(card: Card) {
        cardList.add(card)
    }


}