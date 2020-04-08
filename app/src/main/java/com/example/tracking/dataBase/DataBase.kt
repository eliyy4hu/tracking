package com.example.tracking.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tracking.dataBase.dAO.HabitDao
import com.example.tracking.dataBase.entities.HabitEntity

@Database(entities = [HabitEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}