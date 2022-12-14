package com.example.todo_list.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDAO {
    @Query("SELECT * FROM todoEntity WHERE success = :suc")
    fun getAll(suc:Boolean = false): LiveData<List<ToDoEntity>>

    @Query("SELECT * FROM todoEntity WHERE category = :category AND success = :suc")
    fun getMatchCategory(category:String?, suc:Boolean = false): LiveData<List<ToDoEntity>>

    @Query("SELECT * FROM todoEntity WHERE start_date <= :date AND deadline_date >= :date AND success = :suc")
    fun getCalumOnDate(date:String?, suc: Boolean = false) : LiveData<List<ToDoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDoEntity: ToDoEntity)

    @Query("Delete From todoEntity WHERE id = :id")
    fun delete(id : Int): Int

    @Update
    fun update(toDoEntity: ToDoEntity)

    @Query("Update todoEntity SET success = :suc WHERE id = :id")
    fun success(id : Int, suc : Boolean = true)
}

@Dao
interface CycleDAO {
    @Query("SELECT * FROM cycleEntity")
    fun getAll():LiveData<List<CycleEntity>>

    @Insert
    fun insert(cycleEntity: CycleEntity)

    @Query("UPDATE cycleEntity SET success =:suc")
    fun update(suc: Boolean = false)

    @Query("UPDATE cycleEntity SET success =:suc WHERE id =:id")
    fun todaySuccess(id: Int, suc: Boolean = true)

    @Query("Delete From cycleEntity WHERE id = :id")
    fun delete(id : Int): Int
}
