package com.ece.nsu.spring2021.cse499.arschoolbook.utils

import android.app.AlertDialog
import android.content.Context

object AlertDialogUtil {

    @JvmStatic
    fun showAlertDialogWithTwoButtons(context: Context, title: String = "", message: String,
                                      positiveMessage: String, positiveCallback: Callback,
                                      negativeMessage: String, negativeCallback: Callback)
    {
        val dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveMessage) { dialog, which ->
                positiveCallback.onClick()
                dialog.dismiss()
            }
            .setNegativeButton(negativeMessage) { dialog, _ ->
                negativeCallback.onClick()
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    @JvmStatic
    fun showAlertDialogWithSingleButtons(context: Context, title: String = "", message: String,
                                      buttonLabel: String, buttonCallback: Callback)
    {
        val dialog = AlertDialog.Builder(context)
            .setCancelable(true)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonLabel) { dialog, which ->
                buttonCallback.onClick()
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }

    interface Callback{
        fun onClick()
    }
}