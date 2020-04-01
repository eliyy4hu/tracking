package com.example.tracking.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tracking.Habit
import com.example.tracking.HabitsProvider
import java.util.*

class HabitListViewModel(private val habitType: Int) : ViewModel() {
    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()
    val habits: LiveData<List<Habit>> = mutableHabits
    var namePrefix = ""
    var orderByPriority: Boolean = false

    init {
        load()
    }

    public fun load() {
        var filtered = HabitsProvider.GetHabits()
            .filter {
                it.type == habitType &&
                        it.name.toLowerCase(Locale.ROOT).startsWith(
                            namePrefix.toLowerCase(Locale.ROOT)
                        )
            }
        if (orderByPriority)
            filtered = filtered.sortedBy { it.priority }
        mutableHabits.postValue(filtered)

    }

    fun setNameFilter(namePrefix: String) {
        this.namePrefix = namePrefix
        load()
    }

    fun setPriorityOrder(checked: Boolean) {
        orderByPriority = checked
        load()
    }
}