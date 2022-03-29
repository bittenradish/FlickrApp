package de.example.challenge.flickrapp.dialogs

import android.app.AlertDialog
import android.content.Context
import android.os.CountDownTimer

class ShowDialogs {
    companion object {
        fun showTempAlertDialog(context: Context, message: String, title: String = "Message"):AlertDialog? {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                    "Close"
                ) { thisDialog, which -> thisDialog.dismiss() }
            val dialog: AlertDialog = builder.create()
            dialog.setCancelable(true)
            dialog.show()
            val dialogCloseTimer: CountDownTimer = object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    dialog?.let {
                        if(dialog.isShowing){
                            it.dismiss()
                        }
                    }
                }
            }
            dialogCloseTimer.start()
            return dialog
        }
    }
}