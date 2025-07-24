package com.example.todo_list.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.util.AttributeSet

class ProgressSideLine(context: Context, attrs: AttributeSet) : SideLine(context, attrs) {
    private var state: ProgressState? = null
    private var doColor: Int = Color.BLUE
    private var successColor: Int = Color.GREEN
    private var failColor: Int = Color.RED
    private var waitColor: Int = Color.GRAY
    private var doneLineColor: Int = Color.GREEN

    enum class ProgressState {
        Fail, Success, Wait, Do
    }

    fun setProgressState(state: ProgressState) {
        this.state = state
        invalidate()
    }

    override fun drawLineUp(canvas: Canvas) {
        when (state) {
            ProgressState.Do, ProgressState.Fail, ProgressState.Success -> {
                settingDrawLine(color = doneLineColor)
            }

            ProgressState.Wait -> {
                settingDrawLine(color = waitColor)
                paint.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            }

            else -> Unit
        }

        val x = width / 2F
        val y1 = height / 2F - circleSize
        val y2 = 0F

        canvas.drawLine(x, y1, x, y2, paint)
    }

    override fun drawLineDown(canvas: Canvas) {
        when (state) {
            ProgressState.Success, ProgressState.Fail -> {
                settingDrawLine(color = doneLineColor)
            }

            ProgressState.Do, ProgressState.Wait -> {
                settingDrawLine(color = waitColor)
                paint.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            }

            else -> Unit
        }

        val x = width / 2F
        val y1 = height / 2F + circleSize
        val y2 = height.toFloat()

        canvas.drawLine(x, y1, x, y2, paint)
    }

    override fun drawCircle(canvas: Canvas) {
        paint.color = when (state) {
            ProgressState.Fail -> failColor
            ProgressState.Success -> successColor
            ProgressState.Do -> doColor
            else -> waitColor
        }
        canvas.drawCircle(width / 2F, height / 2F, circleSize.toFloat(), paint)

        settingBackgroundCircle(canvas)
    }
}
