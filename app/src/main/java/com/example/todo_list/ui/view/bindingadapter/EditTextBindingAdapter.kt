package com.example.todo_list.ui.view.bindingadapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter

object EditTextBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:onQueryTextChange")
    fun onQueryTextChange(view: EditText, onChanged: (String) -> Unit){
        view.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}
