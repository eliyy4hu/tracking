package com.example.tracking.viewModels

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.tracking.Habit
import com.example.tracking.dataBase.dbProviders.IHabitProvider
import java.util.*

class HabitListViewModel(
    //private val habitType: Int,
    private val habitProvider: IHabitProvider
) :
    ViewModel() {
    private val mutableHabitsType0: MutableLiveData<List<Habit>> = MutableLiveData()
    private val mutableHabitsType1: MutableLiveData<List<Habit>> = MutableLiveData()
    val habitsType0: LiveData<List<Habit>> = mutableHabitsType0
    val habitsType1: LiveData<List<Habit>> = mutableHabitsType1
    var namePrefix = ""
    var orderByPriority: Boolean = false

    init {
        habitProvider.habits.observeForever( Observer { habits ->
            updateHabits(habits)
        })

    }

    private fun updateHabits(habits: MutableList<Habit>?) {
        var filtered = habits
            ?.filter {

                it.name.toLowerCase(Locale.ROOT).startsWith(
                    namePrefix.toLowerCase(Locale.ROOT)
                )
            }
        if (orderByPriority)
            filtered = filtered?.sortedBy { it.priority }
        val filteredType0 = filtered?.filter { it.type == 0 }
        val filteredType1 = filtered?.filter { it.type == 1 }

        mutableHabitsType0.postValue(filteredType0)
        mutableHabitsType1.postValue(filteredType1)
    }


    fun setNameFilter(namePrefix: String) {
        this.namePrefix = namePrefix
        updateHabits(habitProvider.habits.value)
    }

    fun setPriorityOrder(checked: Boolean) {
        orderByPriority = checked
        updateHabits(habitProvider.habits.value)
    }
}