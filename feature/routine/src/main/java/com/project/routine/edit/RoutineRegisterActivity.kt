package com.project.routine.edit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.project.feature.routine.databinding.ActivityRoutineRegisterBinding
import com.project.permission.TodoPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoutineRegisterActivity : AppCompatActivity() {
    private val viewModel: RoutineRegisterViewModel by viewModels()
    private lateinit var binding: ActivityRoutineRegisterBinding

    @Inject
    lateinit var todoPermission: TodoPermission

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
        todoPermission.notificationPermission()
    }
}
