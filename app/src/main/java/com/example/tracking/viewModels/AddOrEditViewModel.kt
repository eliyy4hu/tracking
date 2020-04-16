package com.example.tracking.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tracking.Habit
import com.example.tracking.dataBase.dbProviders.IHabitProvider
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddOrEditViewModel(
    private val habitProvider: IHabitProvider

) : ViewModel(), CoroutineScope {
    constructor(habit: Habit, habitProvider: IHabitProvider) : this(habitProvider) {
        mutableHabit.postValue(habit)
    }

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    private val mutableHabit: MutableLiveData<Habit> = MutableLiveData()
    val habit: LiveData<Habit> = mutableHabit
    fun setHabit(habit: Habit) {
        mutableHabit.postValue(habit)
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }

    fun saveHabit() = launch {
        withContext(Dispatchers.IO) {
            if (habitProvider.habits.value!!.any { it.id == habit.value!!.id })
                habitProvider.setHabitById(habit.value!!)
            else {
                habitProvider.addHabit(habit.value!!)
            }
        }
    }
}