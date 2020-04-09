package com.example.tracking.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.*
import com.example.tracking.dataBase.dbProviders.HabitsProvider
import com.example.tracking.viewModels.HabitListViewModel
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.habit_list.*


class HabitListFragment() : Fragment(),
    RecyclerViewClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var prioritiesStrings: Array<String>
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var callback: HabitListCallback
    private lateinit var viewModel: HabitListViewModel

    companion object {
        fun newInstance(habitType: Int, prioritiesStrings: Array<String>): HabitListFragment {
            val fr = HabitListFragment()
            val bundle = Bundle()
            bundle.putInt(HABIT_TYPE_KEY, habitType)
            bundle.putStringArray(PRIORITIES_STRINGS, prioritiesStrings)
            fr.arguments = bundle
            return fr
        }

        private const val HABIT_TYPE_KEY = "HABIT_TYPE_KEY"
        private const val PRIORITIES_STRINGS = "PR_STRINGS"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as HabitListCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prioritiesStrings = arguments!!.getStringArray(PRIORITIES_STRINGS)!!
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(activity!!, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return HabitListViewModel(
                        activity!!,
                        HabitsProvider
                    ) as T
                }
            }).get(HabitListViewModel::class.java)}
        viewManager = LinearLayoutManager(activity)
        viewAdapter = HabitListAdapter(
            mutableListOf(),
            this,
            prioritiesStrings
        )
        habit_list.adapter = viewAdapter
        recyclerView = habit_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        val type = arguments!!.getInt(HABIT_TYPE_KEY);
        if (type == 0) {
            viewModel.habitsType0.observe(activity!!, Observer { list ->
                updateAdapter(list)
            })
        } else {
            viewModel.habitsType1.observe(activity!!, Observer { list ->
                updateAdapter(list)
            })
        }

    }





    private fun updateAdapter(habits: List<Habit>) {
        (viewAdapter as HabitListAdapter).habits = habits.toMutableList()
        viewAdapter.notifyDataSetChanged()
    }

    override fun recyclerViewListClicked(v: View?, position: Int) {
        callback.onEdit((viewAdapter as HabitListAdapter).habits[position])
    }
}

interface HabitListCallback {
    fun onEdit(habit: Habit)
}
