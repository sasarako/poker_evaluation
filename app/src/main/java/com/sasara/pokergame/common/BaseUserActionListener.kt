package com.sasara.pokergame.common

/**
 * Created by sasara on 20/1/2018 AD.
 */
interface BaseUserActionListener {

    fun onViewVisible() {
        // optional body
    }

    fun onViewInvisible() {
        // optional body
    }

    fun cleanUp() {
        // optional body
    }

}