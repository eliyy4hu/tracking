package com.example.tracking.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tracking.Habit
import com.example.tracking.dataBase.dbProviders.IHabitProvider

class AddOrEditViewModel(private val habitProvider: IHabitProvider) : ViewModel() {
    constructor(habit: Habit, habitProvider: IHabitProvider) : this(habitProvider) {
        mutableHabit.postValue(habit)
    }

    private val mutableHabit: MutableLiveData<Habit> = MutableLiveData()
    val habit: LiveData<Habit> = mutableHabit
    fun setHabit(habit: Habit) {
        mutableHabit.postValue(habit)
    }

    fun saveHabit() {
        if (habitProvider.habits.value!!.any { it.id == habit.value!!.id })
            habitProvider.setHabitById(habit.value!!)
        else {
            habitProvider.addHabit(habit.value!!)
        }
    }

}