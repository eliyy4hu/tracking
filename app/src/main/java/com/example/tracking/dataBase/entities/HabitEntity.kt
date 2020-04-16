package com.example.tracking.dataBase.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tracking.Habit
import java.util.*

@Entity(tableName = "habit_items")
data class HabitEntity(
    @Embedded val habit: Habit,
    @PrimaryKey
    val key: String = habit.id
    )
