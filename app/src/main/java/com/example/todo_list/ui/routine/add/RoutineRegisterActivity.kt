package com.example.todo_list.ui.routine.add

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.todo_list.R
import com.example.todo_list.databinding.ActivityCycleRegisterBinding
import com.example.todo_list.util.MyApplication
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineRegisterActivity : AppCompatActivity(), TimePicker.OnTimeChangedListener {
    private val viewModel: RoutineRegisterViewModel by viewModels()
    private lateinit var binding: ActivityCycleRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCycleRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val toggle = arrayOf(
            binding.sunday, binding.monday, binding.tuesday,
            binding.wednesday, binding.thursday, binding.friday, binding.saturday
        )

        toggle.forEach {
            it.setOnCheckedChangeListener(DayToggle())
        }

        binding.timePicker.setOnTimeChangedListener(this)

        binding.cycleCancel.setOnClickListener {
            finish()
        }

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

    inner class DayToggle : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
            when (buttonView?.id) {
                binding.sunday.id -> viewModel.checkDay(index = 0, isChecked)
                binding.monday.id -> viewModel.checkDay(index = 1, isChecked)
                binding.tuesday.id -> viewModel.checkDay(index = 2, isChecked)
                binding.wednesday.id -> viewModel.checkDay(index = 3, isChecked)
                binding.thursday.id -> viewModel.checkDay(index = 4, isChecked)
                binding.friday.id -> viewModel.checkDay(index = 5, isChecked)
                binding.saturday.id -> viewModel.checkDay(index = 6, isChecked)
            }

            viewModel.changeCheckedDayText(
                daily = getString(R.string.daily),
                days = resources.getStringArray(R.array.Days)
            )
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.setTime(hourOfDay, minute)
    }
}
