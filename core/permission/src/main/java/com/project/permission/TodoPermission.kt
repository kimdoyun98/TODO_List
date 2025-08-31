package com.project.permission

import android.Manifest
import android.content.Context
import android.os.Build

import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.project.core.ui.R

class TodoPermission(private val context: Context) {
    private val permission = object : PermissionListener {
        override fun onPermissionGranted() {}

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Toast.makeText(
                context,
                context.getString(R.string.permission_denied_toast_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun notificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            TedPermission.create()
                .setPermissionListener(permission)
                .setDeniedMessage(context.getString(R.string.permission_denied_message))
                .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
                .check()
        }
    }
}
