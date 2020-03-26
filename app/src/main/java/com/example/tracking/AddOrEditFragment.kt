package com.example.tracking

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_second_activity.*


class AddOrEditFragment(val habit: Habit) : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance(habit: Habit) = AddOrEditFragment(habit)

        const val HABIT: String = "habit_key"
        const val HABIT_INDEX: String = "habit_index_key"
        const val HABITS: String = "habits_index_key"

    }

    lateinit var callback: AddOrEditFragmentCallback
    lateinit var  types: List<String>


    private fun render() {
        name.setText(habit.name)
        description.setText(habit.description)
        priority.setSelection(habit.priority)
        frequency.setText(habit.frequency)
        val typeIndex = types.indexOf(types[habit.type])
        (type.getChildAt(typeIndex) as RadioButton).isChecked = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as AddOrEditFragmentCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_second_activity, container, false)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        types = type.children.map { (it as RadioButton).text.toString() }.toList()
        activity?.applicationContext?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.priorities,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                priority.adapter = adapter
            }
        }
        priority.onItemSelectedListener = this
        save_changes_btn.setOnClickListener { save(it) }
        render()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        habit.priority = position
    }

    private fun save(view: View) {
        val typeRadioButtonId = type.checkedRadioButtonId
        habit.type =
            types.indexOf(getView()!!.findViewById<RadioButton>(typeRadioButtonId).text)
        habit.frequency = frequency.text.toString()
        habit.description = description.text.toString()
        habit.name = name.text.toString()
        callback.onSave(habit)
    }

}

interface AddOrEditFragmentCallback {
    fun onSave(habit: Habit)
}
