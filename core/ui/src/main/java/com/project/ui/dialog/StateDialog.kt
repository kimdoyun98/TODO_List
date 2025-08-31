package com.project.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.graphics.drawable.toDrawable
import com.project.core.ui.R

object StateDialog {
    fun showDialog(
        context: Context,
        title: String?,
        positiveText: String? = null,
        negativeText: String? = null,
        onClickPositiveButton: () -> Unit,
        onClickNegativeButton: () -> Unit,
    ) {
        val dialog = Dialog(context)
        dialog.apply {
            setContentView(R.layout.dialog_routine_state_layout)
            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        }

        val content = dialog.findViewById<TextView>(R.id.content_tv)
        val negativeButton = dialog.findViewById<AppCompatButton>(R.id.fail_bt)
        val positiveButton = dialog.findViewById<AppCompatButton>(R.id.success_bt)

        positiveText?.let { positiveButton.text = it }
        negativeText?.let { negativeButton.text = it }

        content.text = title
        negativeButton.setOnClickListener {
            onClickNegativeButton()
            dialog.dismiss()
        }

        positiveButton.setOnClickListener {
            onClickPositiveButton()
            dialog.dismiss()
        }

        dialog.show()
    }
}
