package com.example.tracking

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.tracking.fragments.HabitListFragment

class HabitsPagerAdapter(
    childFragmentManager: FragmentManager,
    private val prioritiesStrings: Array<String>
) :
    FragmentStatePagerAdapter(childFragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HabitListFragment.newInstance(0, prioritiesStrings)
            else -> HabitListFragment.newInstance(1, prioritiesStrings)
        }
    }

    override fun getCount(): Int = 2
}


