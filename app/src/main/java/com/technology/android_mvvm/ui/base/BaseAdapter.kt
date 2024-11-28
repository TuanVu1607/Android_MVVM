package com.technology.android_mvvm.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        createCustomViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        bindCustomViewHolder(holder, getItem(position), position)

    override fun getItemViewType(position: Int) = 0

    override fun getItemCount(): Int = currentList.size

    abstract fun createCustomViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun bindCustomViewHolder(holder: RecyclerView.ViewHolder, item: T, position: Int)
}