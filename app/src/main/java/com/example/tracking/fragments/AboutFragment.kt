package com.example.tracking.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.tracking.R


class AboutFragment : Fragment() {

    companion object {
        fun newInstance() = AboutFragment()
    }
    private lateinit var callback: HabitListPagesCallback

    override fun onAttach(context: Context) {
        callback = activity as HabitListPagesCallback
        super.onAttach(context)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.about_fragment, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        callback.onMenuClicked()
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
