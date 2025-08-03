package com.example.todo_list.ui.routine.add

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_list.R
import com.example.todo_list.databinding.ActivityRoutineRegisterBinding
import com.example.todo_list.util.MyApplication
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineRegisterActivity : AppCompatActivity() {
    private val viewModel: RoutineRegisterViewModel by viewModels()
    private lateinit var binding: ActivityRoutineRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoutineRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.activity = this

        binding.cycleRegister.setOnClickListener {
            viewModel.insert(binding.title.text.toString())
            permission()
            finish()
        }
    }

    private fun permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            TedPermission.create()
                .setPermissionListener(permission)
                .setDeniedMessage(getString(R.string.permission_denied_message))
                .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
                .check()
        }
    }

    private val permission = object : PermissionListener {
        override fun onPermissionGranted() {}

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Toast.makeText(
                MyApplication.instance.applicationContext,
                getString(R.string.permission_denied_toast_message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
