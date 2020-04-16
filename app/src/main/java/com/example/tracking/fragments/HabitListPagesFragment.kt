package com.example.tracking.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tracking.HabitsPagerAdapter
import com.example.tracking.R
import com.example.tracking.dataBase.dbProviders.HabitsProvider
import com.example.tracking.viewModels.HabitListViewModel
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.habit_pages_fragment.*


class HabitListPagesFragment() : Fragment() {
    private lateinit var viewModel: HabitListViewModel
    lateinit var callback: HabitListPagesCallback

    companion object {
        fun newInstance() =
            HabitListPagesFragment(
            )
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
        activity?.let {
            viewModel = ViewModelProvider(activity!!, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return HabitListViewModel(
                        HabitsProvider
                    ) as T
                }
            }).get(HabitListViewModel::class.java)
        }
        //resetFilters()
        //viewModel.setPriorityOrder(false)
        //viewModel.setNameFilter("")

        add_habit_btn.setOnClickListener { createNewHabit(it) }
        if (habits_view_pager.adapter == null) {
            habits_view_pager.adapter = HabitsPagerAdapter(
                childFragmentManager,
                resources.getStringArray(R.array.priorities)
            )
            habits_pager_tabLayout.setupWithViewPager(habits_view_pager)
            habits_pager_tabLayout.getTabAt(0)!!.text = "good"
            habits_pager_tabLayout.getTabAt(1)!!.text = "bad"
        }
        setSearchListener()
        priorities_toggle.setOnClickListener { onPrioritiesToggle(it) }
    }

    private fun resetFilters() {
        search_field.setText("")
        priorities_toggle.isChecked = false
    }

    private fun setSearchListener() {
        search_field.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.setNameFilter(p0.toString())
            }
        })
    }

    private fun onPrioritiesToggle(it: View) {
        val checkBox = it as CheckBox
        viewModel.setPriorityOrder(checkBox.isChecked)
    }

    private fun createNewHabit(view: View) {
        viewModel.setNameFilter("")
        viewModel.setPriorityOrder(false)
        search_field.setText("")
        callback.onCreateHabit()
    }
}

interface HabitListPagesCallback {
    fun onCreateHabit()
    fun onMenuClicked()
}
