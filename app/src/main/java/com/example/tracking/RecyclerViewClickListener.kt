package com.example.tracking

import android.view.View

interface RecyclerViewClickListener {
    fun recyclerViewListClicked(v: View?, position: Int)
}