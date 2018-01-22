package com.sasara.pokergame.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by sasara on 21/1/2018 AD.
 */
fun Disposable.addTo(composite: CompositeDisposable): Boolean = composite.add(this)