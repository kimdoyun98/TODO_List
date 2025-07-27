package com.example.todo_list.ui.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import com.example.todo_list.R
import com.example.todo_list.databinding.FragmentHomeBinding
import com.example.todo_list.ui.util.basefragment.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : ViewBindingFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    val openDialog = { position: Int, title: String?, context: Context ->
        val dialog = Dialog(context)
        dialog.apply {
            setContentView(R.layout.dialog_routine_state_layout)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val content = dialog.findViewById<TextView>(R.id.content_tv)
        val failButton = dialog.findViewById<AppCompatButton>(R.id.fail_bt)
        val successButton = dialog.findViewById<AppCompatButton>(R.id.success_bt)

        content.text = title
        failButton.setOnClickListener {
            viewModel.updateToRoutine(position, false)
            dialog.dismiss()
        }

        successButton.setOnClickListener {
            viewModel.updateToRoutine(position, true)
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.fragment = this@HomeFragment
    }
}
