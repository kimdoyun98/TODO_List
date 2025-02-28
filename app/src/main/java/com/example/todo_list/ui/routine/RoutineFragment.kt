package com.example.todo_list.ui.routine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.todo_list.adapter.routine.RoutineAdapter
import com.example.todo_list.alarm.Alarm
import com.example.todo_list.databinding.FragmentRoutineBinding
import com.example.todo_list.ui.routine.add.RoutineRegisterActivity
import com.example.todo_list.ui.util.BottomSheetDialog
import com.example.todo_list.ui.util.Category
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoutineFragment : Fragment() {
    private var _binding: FragmentRoutineBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoutineViewModel by viewModels()

    @Inject
    lateinit var alarm: Alarm

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRoutineRecyclerView()

        binding.addButton.setOnClickListener {
            val intent = Intent(context, RoutineRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRoutineRecyclerView(){
        val adapter = RoutineAdapter ({ routineEntity ->
            BottomSheetDialog(
                context = requireContext(),
                category = Category.ROUTINE,
                entity = routineEntity,
                onClickDelete = {
                    viewModel.delete(routineEntity.id)
                    alarm.cancelAlarm(routineEntity.id)
                },
                onClickDone = {
                    viewModel.todaySuccess(routineEntity.id)
                }
            )
        },
            { id ->
                   viewModel.getRoutineDetails(id)
            },
            viewLifecycleOwner.lifecycleScope
        )
        binding.recyclerview.adapter = adapter

        viewModel.getAll.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }
    }
}
