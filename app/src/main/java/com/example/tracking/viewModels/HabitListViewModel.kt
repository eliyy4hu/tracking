package com.example.tracking.viewModels

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.tracking.Habit
import com.example.tracking.dataBase.providers.IHabitProvider
import java.util.*

class HabitListViewModel(
    private val habitType: Int,
    private val viewLifecycleOwner: LifecycleOwner,
    private val habitProvider: IHabitProvider
) :
    ViewModel() {
    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()
    val habits: LiveData<List<Habit>> = mutableHabits
    var namePrefix = ""
    var orderByPriority: Boolean = false

    init {
        habitProvider.habits.observe(viewLifecycleOwner, Observer { habits ->
            updateHabits(habits)
        })

    }

    private fun updateHabits(habits: MutableList<Habit>?) {
        var filtered = habits
            ?.filter {
                it.type == habitType &&
                        it.name.toLowerCase(Locale.ROOT).startsWith(
                            namePrefix.toLowerCase(Locale.ROOT)
                        )
            }
        if (orderByPriority)
            filtered = filtered?.sortedBy { it.priority }
        mutableHabits.postValue(filtered)
    }


    fun setNameFilter(namePrefix: String) {
        this.namePrefix = namePrefix
        updateHabits(mutableHabits.value?.toMutableList())
    }

    fun setPriorityOrder(checked: Boolean) {
        orderByPriority = checked
        updateHabits(mutableHabits.value?.toMutableList())
    }
}