package com.example.tracking.dataBase.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tracking.Habit
import java.util.*

@Entity(tableName = "habit_items")
data class HabitEntity(
    @PrimaryKey
    var id: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "priority")
    var priority: Int,
    @ColumnInfo(name = "type")
    var type: Int,
    @ColumnInfo(name = "frequency")
    var frequency: String
) {
    constructor(habit: Habit) : this(
        habit.id.toString(),
        habit.name,
        habit.description,
        habit.priority,
        habit.type,
        habit.frequency
    )
    fun toHabit(): Habit {
        var habit = Habit()
        habit.name = name
        habit.id = UUID.fromString(id)
        habit.priority = priority
        habit.description = description
        habit.type = type
        habit.frequency = frequency
        return habit
    }
}