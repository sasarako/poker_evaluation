package com.sasara.pokergame.extension

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import com.sasara.pokergame.R

/**
 * Created by sasara on 25/1/2018 AD.
 */

fun Context?.showAlertDialog(message: String? = null,
                             cancelable: Boolean = false) {
    if (this == null) {
        return
    }
    if (this is Activity && this.isFinishing) {
        return
    }

    val builder = AlertDialog.Builder(this).apply {
        setMessage(message)
                .setPositiveButton(R.string.alert_error_ok,
                        { dialog, id ->
                            dialog.dismiss()
                        })
        setCancelable(cancelable)
    }

    try {
        builder.show()
    } catch (e: IllegalStateException) {
        e.printStackTrace()
    }
}
