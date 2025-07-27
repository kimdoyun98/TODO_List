package com.example.todo_list.ui.view.bindingadapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.example.todo_list.R

object EditTextBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:onQueryTextChange")
    fun onQueryTextChange(view: EditText, onChanged: (String) -> Unit) {
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    @JvmStatic
    @BindingAdapter("app:date_format")
    fun onQueryTextChangeDate(view: EditText, onChanged: (String) -> Unit) {
        val mask = "####-##-##"
        val maxLength = mask.length

        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                regexDate()
            }

            private fun regexDate() {
                val cleanString = view.text.toString().replace("\\D".toRegex(), "")
                val maskBuffer = StringBuilder()
                var maskIndex = 0
                var cleanIndex = 0

                while (maskIndex < maxLength && cleanIndex < cleanString.length) {
                    if (mask[maskIndex] == '#') {
                        maskBuffer.append(cleanString[cleanIndex])
                        cleanIndex++
                    } else {
                        maskBuffer.append(mask[maskIndex])
                    }
                    maskIndex++
                }

                onChanged(maskBuffer.toString())
                view.setSelection(view.text.length)
            }
        })
    }

    @JvmStatic
    @BindingAdapter("app:is_vaild")
    fun isVaildEditText(view: EditText, isVaild: Boolean){
        view.background = AppCompatResources.getDrawable(
            view.context,
            if(isVaild) R.drawable.edittext_vaild_border
            else R.drawable.edittext_invaild_border
        )
    }
}
