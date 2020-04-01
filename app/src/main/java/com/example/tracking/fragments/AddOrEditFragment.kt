package com.example.tracking.fragments

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tracking.Habit
import com.example.tracking.R
import com.example.tracking.viewModels.AddOrEditViewModel
import com.example.tracking.viewModels.HabitListViewModel
import kotlinx.android.synthetic.main.add_or_edit_fragment.*


class AddOrEditFragment() : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance(habit: Habit): AddOrEditFragment {
            val fr = AddOrEditFragment()
            val bundle = Bundle()
            bundle.putParcelable(HABIT_KEY, habit)
            fr.arguments = bundle
            return fr
        }

        private const val HABIT_KEY: String = "habit_key"

    }

    private lateinit var viewModel: AddOrEditViewModel
    lateinit var callback: AddOrEditFragmentCallback
    lateinit var types: List<String>


    private fun render(habit: Habit) {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AddOrEditViewModel(arguments!!.getParcelable<Habit>(HABIT_KEY)!!) as T
            }
        }).get(AddOrEditViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.add_or_edit_fragment, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        callback.onBack()
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
        viewModel.habit.observe(viewLifecycleOwner, Observer { habit ->
            render(habit)
        })
        //viewModel.setHabit(habit)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val habit = viewModel.habit.value!!
        habit.priority = position
        viewModel.setHabit(habit)
    }

    private fun save(view: View) {
        val typeRadioButtonId = type.checkedRadioButtonId
        viewModel.habit.value!!.type =
            types.indexOf(getView()!!.findViewById<RadioButton>(typeRadioButtonId).text)
        viewModel.habit.value!!.frequency = frequency.text.toString()
        viewModel.habit.value!!.description = description.text.toString()
        viewModel.habit.value!!.name = name.text.toString()
        callback.onSave(viewModel.habit.value!!)
    }

}

interface AddOrEditFragmentCallback {
    fun onSave(habit: Habit)
    fun onBack()
}
