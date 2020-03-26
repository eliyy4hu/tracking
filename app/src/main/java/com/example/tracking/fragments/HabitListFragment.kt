package com.example.tracking.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.Habit
import com.example.tracking.HabitListAdapter
import com.example.tracking.R
import com.example.tracking.RecyclerViewClickListener
import kotlinx.android.synthetic.main.habit_list.*


class HabitListFragment(val habits: List<Habit>, val prioritiesStrings: Array<String>) : Fragment(),
    RecyclerViewClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var callback: HabitListCallback

    companion object {
        fun newInstance(habits: List<Habit>, prioritiesStrings: Array<String>) =
            HabitListFragment(
                habits,
                prioritiesStrings
            )

        public const val FRAGMENT_TAG = "habit_list_fragment_tag"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as HabitListCallback
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(activity)
        viewAdapter = HabitListAdapter(
            habits,
            this,
            prioritiesStrings
        )
        habit_list.adapter = viewAdapter
        recyclerView = habit_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun recyclerViewListClicked(v: View?, position: Int) {
        callback.onEdit(habits[position])

    }

}

interface HabitListCallback {
    fun onEdit(habit: Habit)
}
