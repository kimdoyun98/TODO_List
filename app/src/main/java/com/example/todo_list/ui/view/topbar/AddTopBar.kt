package com.example.todo_list.ui.view.topbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.todo_list.R
import com.example.todo_list.databinding.AddTopBarLayoutBinding

class AddTopBar(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private val binding: AddTopBarLayoutBinding =
        AddTopBarLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var onBackButtonClick: () -> Unit?

    init {
        val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.AddTopBar, 0, 0)

        binding.title.text = attr.getString(R.styleable.AddTopBar_title).toString()
        binding.backBt.setOnClickListener {
            onBackButtonClick()
        }
    }

    fun setOnClickListener(onClick: () -> Unit?){
        onBackButtonClick = onClick
    }

    object AddTopBarBindingAdapter{
        @JvmStatic
        @BindingAdapter("app:onBackClick")
        fun setBackButtonClick(view: AddTopBar, onClick: () -> Unit?){
            view.setOnClickListener(onClick)
        }
    }
}
