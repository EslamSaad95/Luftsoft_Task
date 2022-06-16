package com.example.luftsofttask.presentation.extension

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.linearLayoutManager(@RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL) {
    layoutManager = LinearLayoutManager(this.context, orientation, false)
}

fun RecyclerView.addItemDecoration(@RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL) {
    val dividerItemDecoration = DividerItemDecoration(
        context,
        orientation
    )
    this.addItemDecoration(dividerItemDecoration)
}
