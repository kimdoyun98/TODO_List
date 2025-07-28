package com.example.todo_list.ui.home

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.todo_list.databinding.FragmentHomeBinding
import com.example.todo_list.ui.util.StateDialog
import com.example.todo_list.ui.util.basefragment.ViewBindingFragment
import com.example.todo_list.widget.routine.RoutineWidgetProvider
import com.example.todo_list.widget.schedule.ScheduleWidgetProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    val openDialog = { position: Int, title: String?, context: Context ->
        StateDialog.showDialog(
            context = context,
            title = title,
            onClickPositiveButton = { viewModel.updateToRoutine(position, true) },
            onClickNegativeButton = { viewModel.updateToRoutine(position, false) }
        )
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.fragment = this@HomeFragment

        fragmentScope {
            launch {
                viewModel.todayRoutine.collect {
                    routineWidgetUpdate()
                }
            }

            launch {
                viewModel.recentSchedule.collect {
                    scheduleWidgetUpdate()
                }
            }
        }
    }

    private fun scheduleWidgetUpdate() {
        val intent = Intent(requireContext(), ScheduleWidgetProvider::class.java).apply {
            setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        }

        val appWidgetManager = AppWidgetManager.getInstance(requireContext())
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(
                requireContext(),
                ScheduleWidgetProvider::class.java
            )
        )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

        activity?.sendBroadcast(intent)
    }

    private fun routineWidgetUpdate() {
        val intent = Intent(requireContext(), RoutineWidgetProvider::class.java).apply {
            setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        }

        val appWidgetManager = AppWidgetManager.getInstance(requireContext())
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(
                requireContext(),
                RoutineWidgetProvider::class.java
            )
        )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

        activity?.sendBroadcast(intent)
    }
}
