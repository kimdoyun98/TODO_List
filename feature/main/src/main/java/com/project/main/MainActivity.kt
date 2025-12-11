package com.project.main

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.project.feature.main.R
import com.project.feature.main.databinding.ActivityMainBinding
import com.project.ui.dialog.BatteryDialog
import com.project.ui.turnOffDarkMode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        turnOffDarkMode()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
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
