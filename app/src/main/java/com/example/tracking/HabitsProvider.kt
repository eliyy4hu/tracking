package com.example.tracking

class HabitsProvider {
    companion object {
        private var habits: MutableList<Habit>? = null;
        public fun GetHabits(): MutableList<Habit> {
            if (habits == null) {
                habits = mutableListOf(
                    Habit("GOOD", "", 0, 0, ""),
                    Habit("BAD1", "", 0, 1, ""),
                    Habit("BAD2", "", 0, 1, "")
                )
            }
            return habits as MutableList<Habit>
        }


        fun AddHabit(habit: Habit) {
            habits!!.add(habit)
        }
        fun SetHabitById(habit: Habit) {
            val habitToReplace = habits!!.indexOfFirst { it.id == habit.id }
            habits!![habitToReplace] = habit;
        }
    }
}