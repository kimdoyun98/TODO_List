package com.example.todo_list.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class BatteryDialog: DialogFragment() {
    @SuppressLint("BatteryLife")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message =
            "정상적인 앱 사용을 위해 해당 어플을 \"배터리 사용량 최적화\" 목록에서 \"제외\"해야 합니다. \n\n[확인] 버튼을 누른 후 시스템 알림 대화 상자가 뜨면 [허용] 을 선택해 주세요"
        val clickListener: DialogInterface.OnClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                dismiss()

                val intent = Intent()
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                intent.setData(Uri.parse("package:" + context?.packageName))
                startActivity(intent)
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(message)
            .setPositiveButton("확인", clickListener)
        return builder.create()
    }

    companion object {
        const val TAG = "BatteryDialog"
        fun newInstance(): BatteryDialog {
            return BatteryDialog()
        }
    }
}
