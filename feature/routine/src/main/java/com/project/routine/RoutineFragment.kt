package com.project.routine

import android.content.Intent
import androidx.fragment.app.viewModels
import com.project.alarm.Alarm
import com.project.feature.routine.databinding.FragmentRoutineBinding
import com.project.model.Routine
import com.project.routine.edit.RoutineRegisterActivity
import com.project.ui.base.DataBindingFragment
import com.project.ui.dialog.StateDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoutineFragment :
    DataBindingFragment<FragmentRoutineBinding>(FragmentRoutineBinding::inflate) {
    private val viewModel: RoutineViewModel by viewModels()

    @Inject
    lateinit var alarm: Alarm

    val onClick = { routineEntity: Routine ->
        StateDialog.showDialog(
            context = requireContext(),
            title = routineEntity.title,
            negativeText = "삭제",
            onClickPositiveButton = { viewModel.todaySuccess(routineEntity.id) },
            onClickNegativeButton = {
                viewModel.delete(routineEntity.id)
                alarm.cancelAlarm(routineEntity.id)
            }
        )
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment = this
    }

    fun addRoutine() {
        val intent = Intent(context, RoutineRegisterActivity::class.java)
        startActivity(intent)
    }
}
