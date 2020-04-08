package com.example.tracking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HabitListAdapter(
    var habits: MutableList<Habit>,
    private val recyclerViewClickListener: RecyclerViewClickListener,
    private val prioritiesStrings: Array<String>

) : RecyclerView.Adapter<HabitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(
            inflater.inflate(R.layout.habit_item, parent, false),
            recyclerViewClickListener, prioritiesStrings
        )
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }
}