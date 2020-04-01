package com.example.tracking.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tracking.Habit
import com.example.tracking.HabitsProvider
import java.util.*

class AddOrEditViewModel() : ViewModel() {
    constructor(habit: Habit) : this() {
        mutablehabit.postValue(habit)
    }

    private val mutablehabit: MutableLiveData<Habit> = MutableLiveData()
    val habit: LiveData<Habit> = mutablehabit
    public fun setHabit(habit: Habit) {
        mutablehabit.postValue(habit)
    }

}