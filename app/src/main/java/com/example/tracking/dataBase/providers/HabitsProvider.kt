package com.example.tracking.dataBase.providers

import androidx.lifecycle.*
import com.example.tracking.Habit
import com.example.tracking.dataBase.AppDatabase
import com.example.tracking.dataBase.dAO.HabitDao
import com.example.tracking.dataBase.entities.HabitEntity

object HabitsProvider : IHabitProvider {
    override var habits: MutableLiveData<MutableList<Habit>> = MutableLiveData()
    override fun setDb(database: AppDatabase, lifecycleOwner: LifecycleOwner) {
        db = database
        habitDao = db.habitDao()
        habitDao.getAll().observe(lifecycleOwner, Observer { habits ->
            updateHabits(
                habits
            )
        })
    }

    private lateinit var db: AppDatabase
    private lateinit var habitDao: HabitDao

    private fun updateHabits(habits: List<HabitEntity>?) {
        HabitsProvider.habits.postValue(habits?.map { it.toHabit() }?.toMutableList())
    }

    override fun addHabit(habit: Habit) {
        habitDao.insertAll(HabitEntity(habit))
    }

    override fun setHabitById(habit: Habit) {
        habitDao.updateHabit(HabitEntity(habit))
    }
}