package com.example.todo_list

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todo_list.data.CycleEntity
import com.example.todo_list.data.CycleRepository
import com.example.todo_list.data.ToDoEntity
import com.example.todo_list.data.ToDoRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var calendar = Calendar.getInstance()
    private val todoRepository : ToDoRepository = ToDoRepository.getInstance()
    private val cycleRepository : CycleRepository = CycleRepository.getInstance()
    private val today = MutableLiveData<Int>()
    private val day = calendar.get(Calendar.DAY_OF_WEEK)
    private val selectionFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    init {
        today.value = day
    }

    fun getAll() : LiveData<List<CycleEntity>>{
        return cycleRepository.selectAll()
    }

    fun getDay() : MutableLiveData<Int> {
        return this.today
    }

    fun getDDay(deadline : String): String {
        val todayDate = calendar.time.time
        val endDate = SimpleDateFormat("yyyyMMdd").parse(deadline).time
        val today = selectionFormatter.format(LocalDate.now())

        return if (today == deadline) "D-Day"
               else {
                   val D_Day = (endDate - todayDate) / (24 * 60 * 60 * 1000) + 1
                   Log.d("D-Day", D_Day.toString())
                   "D-$D_Day"
               }
    }

    fun getPersonal() : LiveData<List<ToDoEntity>> {
        return todoRepository.selectPersonal()
    }

    fun getProject() : LiveData<List<ToDoEntity>> {
        return todoRepository.selectProject()
    }
}