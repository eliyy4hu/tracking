package com.example.tracking.dataBase.dAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tracking.dataBase.entities.HabitEntity

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_items")
    fun getAll(): LiveData<List<HabitEntity>>

    @Insert
    fun insertAll(vararg habit: HabitEntity)

    @Query("SELECT * FROM habit_items WHERE id LIKE :id")
    fun findByHabitId(id: String): HabitEntity

    @Delete
    fun delete(habit: HabitEntity)

    @Update
    fun updateHabit(vararg habit: HabitEntity)
}