package com.example.tracking

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.nav_bar.*


class MainActivity() : AppCompatActivity(), AddOrEditFragmentCallback, HabitListPagesCallback,
    HabitListCallback {
    var habits = mutableListOf<Habit>(
        Habit("GOOD", "", 0, 0, ""),
        Habit("BAD1", "", 0, 1, ""),
        Habit("BAD2", "", 0, 1, "")
    )

    lateinit var navController: NavController
    lateinit var prioritiesStrings: Array<String>
    private lateinit var drawerToggle: ActionBarDrawerToggle


    companion object {
        const val HABITS: String = "habits_key"
        const val HABIT_PAGES_FRAGMENT_TAG: String = "habit_pages"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_bar)
        drawerToggle = ActionBarDrawerToggle(
            this,
            navigation_drawer_layout,
            R.string.open_second_activity,
            R.string.open_second_activity
        )
        navigation_drawer_layout.addDrawerListener(drawerToggle)
        supportActionBar?.title = "Ha! Bits"
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_launcher_background)


        //navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        prioritiesStrings = resources.getStringArray(R.array.priorities)

        if (savedInstanceState == null) {
            val habitListPagesFragment: HabitListPagesFragment =
                HabitListPagesFragment.newInstance(habits, prioritiesStrings)
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    habitListPagesFragment,
                    HABIT_PAGES_FRAGMENT_TAG
                )
                .commit()
        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        navigation_drawer_layout.openDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray(HABITS, habits.toTypedArray())
    }

    override fun onSave(habit: Habit) {
        val habitToReplace = habits.indexOfFirst { it.id == habit.id }
        habits[habitToReplace] = habit
        val habitListPagesFragment: HabitListPagesFragment =
            HabitListPagesFragment.newInstance(habits, prioritiesStrings)

        clearBackStack()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                habitListPagesFragment,
                HABIT_PAGES_FRAGMENT_TAG
            )
            .commit()
    }

    private fun clearBackStack() {
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateHabit() {
        val habit = Habit()
        habits.add(habit)
        val addOrEditFragment = AddOrEditFragment.newInstance(habit)
        //(activity!! as MainActivity).navController.navigate(R.id.addOrEditFragment2)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, addOrEditFragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onEdit(habit: Habit) {
        val addOrEditFragment = AddOrEditFragment(habit)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, addOrEditFragment)
            .addToBackStack(null)
            .commit()
    }

    fun home(item: MenuItem) {
        val habitListPagesFragment: HabitListPagesFragment =
            HabitListPagesFragment.newInstance(habits, prioritiesStrings)
        clearBackStack()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                habitListPagesFragment,
                HABIT_PAGES_FRAGMENT_TAG
            )
            .commit()
        navigation_drawer_layout.closeDrawers()
    }

    fun about(item: MenuItem) {
        val aboutFragment = AboutFragment.newInstance()
        clearBackStack()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                aboutFragment,
                HABIT_PAGES_FRAGMENT_TAG
            )
            .commit()
        navigation_drawer_layout.closeDrawers()
    }


}
