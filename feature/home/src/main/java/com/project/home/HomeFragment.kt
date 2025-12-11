package com.project.home

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import com.project.feature.home.databinding.FragmentHomeBinding
import com.project.ui.base.DataBindingFragment
import com.project.ui.dialog.StateDialog
import com.project.widget.routine.RoutineWidgetProvider
import com.project.widget.schedule.ScheduleWidgetProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class HomeFragment : DataBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
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
