package com.project.ui.view.bindingadapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.project.core.ui.R

object EditTextBindingAdapter {
    private const val DATE_MASK = "####-##-##"
    private const val MASK = '#'
    private const val MAX_LENGTH = DATE_MASK.length
    private const val DATE_FORMAT = "\\D"

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
        view.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                regexDate()
            }

            private fun regexDate() {
                val cleanString = view.text.toString().replace(DATE_FORMAT.toRegex(), "")
                val maskBuffer = StringBuilder()
                var maskIndex = 0
                var cleanIndex = 0

                while (maskIndex < MAX_LENGTH && cleanIndex < cleanString.length) {
                    if (DATE_MASK[maskIndex] == MASK) {
                        maskBuffer.append(cleanString[cleanIndex])
                        cleanIndex++
                    } else {
                        maskBuffer.append(DATE_MASK[maskIndex])
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
    fun isVaildEditText(view: EditText, isVaild: Boolean) {
        view.background = AppCompatResources.getDrawable(
            view.context,
            if (isVaild) R.drawable.edittext_vaild_border
            else R.drawable.edittext_invaild_border
        )
    }
}
