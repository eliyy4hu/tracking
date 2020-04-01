package com.example.tracking

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.tracking.fragments.*
import kotlinx.android.synthetic.main.nav_bar.*


class MainActivity() : AppCompatActivity(),
    AddOrEditFragmentCallback,
    HabitListPagesCallback,
    HabitListCallback {


    lateinit var navController: NavController
    lateinit var prioritiesStrings: Array<String>
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private var isOpen: Boolean = false


    companion object {
        const val HABITS: String = "habits_key"
        const val HABIT_PAGES_FRAGMENT_TAG: String = "habit_pages"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_bar)
//        drawerToggle = ActionBarDrawerToggle(
//            this,
//            navigation_drawer_layout,
//            R.string.open_second_activity,
//            R.string.open_second_activity
//        )
        //navigation_drawer_layout.addDrawerListener(drawerToggle)
        supportActionBar?.title = "Ha! Bits"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_burger)
        if (savedInstanceState == null) {
            val habitListPagesFragment: HabitListPagesFragment =
                HabitListPagesFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    habitListPagesFragment,
                    HABIT_PAGES_FRAGMENT_TAG
                )
                .commit()
        }


    }

    private fun openRootFragment(fragment: Fragment) {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_burger)
        clearBackStack()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                fragment,
                HABIT_PAGES_FRAGMENT_TAG
            )
            .commit()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArray(HABITS, HabitsProvider.GetHabits().toTypedArray())
    }

    override fun onSave(habit: Habit) {
        HabitsProvider.SetHabitById(habit)
        val habitListPagesFragment: HabitListPagesFragment =
            HabitListPagesFragment.newInstance()
        openRootFragment(fragment = habitListPagesFragment)
    }

    override fun onBack() {

        val habitListPagesFragment: HabitListPagesFragment =
            HabitListPagesFragment.newInstance()
        openRootFragment(habitListPagesFragment)
    }

    private fun clearBackStack() {
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateHabit() {
        val habit = Habit()
        HabitsProvider.AddHabit(habit)
        val addOrEditFragment = AddOrEditFragment.newInstance(habit)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, addOrEditFragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onMenuClicked() {
        isOpen = navigation_drawer_layout.isDrawerOpen(navigation_drawer)
        if (!isOpen)
            navigation_drawer_layout.openDrawer(GravityCompat.START)
        else
            navigation_drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun onEdit(habit: Habit) {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow)

        val addOrEditFragment =
            AddOrEditFragment.newInstance(habit)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, addOrEditFragment)
            .addToBackStack(null)
            .commit()
    }

    fun home(item: MenuItem) {
        val habitListPagesFragment: HabitListPagesFragment =
            HabitListPagesFragment.newInstance()
        openRootFragment(habitListPagesFragment)
        navigation_drawer_layout.closeDrawers()

    }

    fun about(item: MenuItem) {
        val aboutFragment = AboutFragment.newInstance()
        openRootFragment(aboutFragment)
        navigation_drawer_layout.closeDrawers()
    }


}
