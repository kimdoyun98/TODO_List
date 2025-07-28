package com.example.todo_list.ui.routine

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.todo_list.alarm.Alarm
import com.example.todo_list.data.room.RoutineEntity
import com.example.todo_list.databinding.FragmentRoutineBinding
import com.example.todo_list.ui.routine.add.RoutineRegisterActivity
import com.example.todo_list.ui.util.StateDialog
import com.example.todo_list.ui.util.basefragment.DataBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoutineFragment :
    DataBindingFragment<FragmentRoutineBinding>(FragmentRoutineBinding::inflate) {
    private val viewModel: RoutineViewModel by viewModels()

    @Inject
    lateinit var alarm: Alarm

    val onClick = { routineEntity: RoutineEntity ->
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
