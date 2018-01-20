package com.sasara.pokergame.common

import android.content.Context

/**
 * Created by sasara on 20/1/2018 AD.
 */
class ObservableError(var errorMessage: String?, var errorType: Int) : Throwable() {
    companion object {
        //Error code
        const val UNDEFINED = 0
        const val NO_AUTH = 1
        const val CANNOT_GET_POINT = 2
        const val POINT_NOT_ENOUGH = 3
    }

    fun getErrorMsg(context: Context?): String {
        return when (errorType) {

            UNDEFINED -> ""
            NO_AUTH -> ""
            CANNOT_GET_POINT -> ""
            else -> ""

        }
    }

}