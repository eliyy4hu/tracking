package com.example.tracking

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_or_edit_fragment.*

class HabitViewHolder(
    itemView: View,
    var recyclerViewClickListener: RecyclerViewClickListener, var prioritiesStrings: Array<String>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


    private val nameText: TextView = itemView.findViewById(R.id.name)
    private val descriptionText: TextView = itemView.findViewById(R.id.description)
    private val priorityText: TextView = itemView.findViewById(R.id.priority)
    private val typeText: TextView = itemView.findViewById(R.id.type)
    private val frequencyText: TextView = itemView.findViewById(R.id.frequency)

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(habit: Habit) {
        nameText.text = habit.name
        descriptionText.text = habit.description
        priorityText.text = prioritiesStrings[habit.priority]
        typeText.text = if (habit.type == 0) "good" else "bad";
        frequencyText.text = habit.frequency
    }

    override fun onClick(v: View?) {
        recyclerViewClickListener.recyclerViewListClicked(v, this.layoutPosition);
    }
}