package com.example.todo_list.ui

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todo_list.R
import com.example.todo_list.databinding.ActivityMainBinding
import com.example.todo_list.util.BatteryDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.main_fragment)
        binding.navBar.setupWithNavController(navController)

        viewModel

        addWhiteListToBattery()
    }

    private fun addWhiteListToBattery() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
            val batteryDialog = BatteryDialog.newInstance()
            batteryDialog.show(supportFragmentManager, BatteryDialog.TAG)
        }
    }
}
