package com.example.tracking.dataBase.dbProviders

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.tracking.Habit
import com.example.tracking.dataBase.AppDatabase

interface IHabitProvider {
    val habits: LiveData<MutableList<Habit>>
    fun setDb(database: AppDatabase, lifecycleOwner: LifecycleOwner)
    fun addHabit(habit: Habit)
    fun setHabitById(habit: Habit)
}
