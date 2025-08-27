package com.project.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.core.ui.R
import com.project.core.ui.databinding.BottomsheetLayoutBinding
import com.project.data.local.room.entity.RoutineEntity
import com.project.data.local.room.entity.ScheduleEntity

class BottomSheetDialog<out T>(
    context: Context,
    @StyleRes theme: Int = R.style.BottomSheetTheme,
    private val category: Category,
    private val entity: T,
    private val onClickUpdate: () -> Unit = {},
    private val onClickDelete: () -> Unit = {},
    private val onClickDone: () -> Unit = {}
) {
    private var _binding: BottomsheetLayoutBinding? = null
    private val binding get() = _binding!!
    private var bottomSheetDialog: BottomSheetDialog

    init {
        _binding = BottomsheetLayoutBinding.inflate(LayoutInflater.from(context))
        initMenu(context)

        bottomSheetDialog = BottomSheetDialog(context, theme)
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()
    }

    private fun initMenu(context: Context) {
        val bottomSheetAdapter = BottomSheetAdapter(
            onClickUpdate = {
                onClickUpdate()
                _binding = null
                bottomSheetDialog.dismiss()
            },
            onClickDelete = {
                showDeleteDialog(context, entity)
                _binding = null
                bottomSheetDialog.dismiss()
            },
            onClickDone = {
                onClickDone()
                _binding = null
                bottomSheetDialog.dismiss()
            }
        )

        when (category) {
            Category.ROUTINE -> {
                bottomSheetAdapter.submitList(
                    listOf(MenuList.DONE, MenuList.DELETE)
                )
            }

            Category.SCHEDULE -> {
                bottomSheetAdapter.submitList(
                    listOf(MenuList.DONE, MenuList.UPDATE, MenuList.DELETE)
                )
            }
        }
        binding.menuRv.adapter = bottomSheetAdapter
    }

    private fun showDeleteDialog(context: Context, entity: T) {
        when (entity) {
            is RoutineEntity -> {
                deleteDialog(context, entity.title)
            }

            is ScheduleEntity -> {
                deleteDialog(context, entity.title)
            }
        }
    }

    private fun deleteDialog(context: Context, title: String?) {
        AlertDialog.Builder(context).apply {
            setTitle(context.getString(R.string.routine_delete_dialog_title))
            setMessage(title)
            setPositiveButton(context.getString(R.string.dialog_positive_text)) { _, _ ->
                onClickDelete()
            }
            setNegativeButton(context.getString(R.string.dialog_negative_text)) { _, _ ->

            }
            show()
        }
    }
}

enum class MenuList(val title: String) {
    UPDATE("수정"),
    DELETE("삭제"),
    DONE("완료")
}

enum class Category {
    SCHEDULE, ROUTINE
}
