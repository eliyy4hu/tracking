package com.example.tracking

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_second_activity.*
import kotlinx.android.synthetic.main.habit_pages_fragment.*


class HabitListPagesFragment(var habits: MutableList<Habit>, val prioritiesStrings:Array<String>) : Fragment() {
    lateinit var callback: HabitListPagesCallback
    companion object {
        fun newInstance(habits: MutableList<Habit>,prioritiesStrings:Array<String>) = HabitListPagesFragment(habits,prioritiesStrings)
        const val HABIT: String = "habit_key"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as HabitListPagesCallback
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        callback.onMenuClicked()
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.habit_pages_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_habit_btn.setOnClickListener { createNewHabit(it) }

        if (habits_view_pager.adapter == null) {
            habits_view_pager.adapter = HabitsPagerAdapter(childFragmentManager, habits,prioritiesStrings)
            habits_pager_tabLayout.setupWithViewPager(habits_view_pager)
            habits_pager_tabLayout.getTabAt(0)!!.text = "good"
            habits_pager_tabLayout.getTabAt(1)!!.text = "bad"
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun createNewHabit(view: View) {
        callback.onCreateHabit()

    }




}
interface HabitListPagesCallback{
    fun onCreateHabit()
    fun onMenuClicked()
}
