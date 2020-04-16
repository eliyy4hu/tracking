package com.example.tracking

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.tracking.dataBase.AppDatabase
import com.example.tracking.dataBase.dbProviders.HabitsProvider
import com.example.tracking.dataBase.dbProviders.IHabitProvider
import com.example.tracking.fragments.*
import kotlinx.android.synthetic.main.nav_bar.*


class MainActivity() : AppCompatActivity(),
    AddOrEditFragmentCallback,
    HabitListPagesCallback,
    HabitListCallback {
    private var isOpen: Boolean = false

    companion object {
        const val HABIT_PAGES_FRAGMENT_TAG: String = "habit_pages"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_bar)
        setDb(HabitsProvider) //todo application class
        setActionBar()
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

    private fun setDb(habitProvider: IHabitProvider) {
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "habits.db"
        )
            .fallbackToDestructiveMigration()
            .build()
        habitProvider.setDb(db, this)
    }

    private fun setActionBar() {
        supportActionBar?.title = "Ha! Bits"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_burger)
    }

    private fun openRootFragment(fragment: Fragment) {
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_burger)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                fragment,
                HABIT_PAGES_FRAGMENT_TAG
            )
            .commit()
    }

    override fun onSave() {
        val habitListPagesFragment: HabitListPagesFragment =
            HabitListPagesFragment.newInstance()
        openRootFragment(fragment = habitListPagesFragment)
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_burger)
    }

    override fun onCreateHabit() {
        val habit = null
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
