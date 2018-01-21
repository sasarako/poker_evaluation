package com.sasara.pokergame.common

import java.lang.ref.WeakReference
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by sasara on 20/1/2018 AD.
 */

class ReadWriteWeakRefDelegate<in R, T>() : ReadWriteProperty<R, T?> {
    private var weakRef: WeakReference<T?>? = null

    constructor(obj: T) : this() {
        weakRef = WeakReference(obj)
    }

    override fun getValue(thisRef: R, property: KProperty<*>): T? = weakRef?.get()
    override fun setValue(thisRef: R, property: KProperty<*>, value: T?) {
        weakRef = WeakReference(value)
    }
}

class ReadWeakRefDelegate<in R, out T>(obj: T) : ReadOnlyProperty<R, T?> {
    private val weakRef = WeakReference(obj)
    override fun getValue(thisRef: R, property: KProperty<*>): T? = weakRef.get()
}