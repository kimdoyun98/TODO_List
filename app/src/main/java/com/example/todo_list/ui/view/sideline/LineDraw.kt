package com.example.todo_list.ui.view.sideline

import android.graphics.Canvas

interface LineDraw {
    fun drawLineUp(canvas: Canvas)
    fun drawLineDown(canvas: Canvas)
    fun drawCircle(canvas: Canvas)
}
