package com.sasara.pokergame.domain.usecase

import io.reactivex.Observable

/**
 * Created by sasara on 19/1/2018 AD.
 */
interface UseCase

interface ObservableUseCase<T> : UseCase {
    fun toObservable(): Observable<T>

}