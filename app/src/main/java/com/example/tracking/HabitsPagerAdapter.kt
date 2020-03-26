package com.example.tracking

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.tracking.fragments.HabitListFragment

class HabitsPagerAdapter(
    childFragmentManager: FragmentManager,
    var habits: List<Habit>,
    val prioritiesStrings: Array<String>
) :
    FragmentStatePagerAdapter(childFragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HabitListFragment.newInstance(habits.filter { it.type == 0 },prioritiesStrings)
            else -> HabitListFragment.newInstance(habits.filter { it.type == 1 },prioritiesStrings)
        }
    }

    override fun getCount(): Int = 2
}


