package com.technology.android_mvvm.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.technology.android_mvvm.common.interfaces.ItemClickListener
import com.technology.android_mvvm.data.model.ExampleModel
import com.technology.android_mvvm.ui.base.BaseAdapter
import com.technology.android_mvvm.ui.base.BaseDiffUtil
import com.technology.android_mvvm.R
import com.technology.android_mvvm.databinding.ItemExampleModelBinding
import com.technology.android_mvvm.ui.viewholders.ExampleModelViewHolder

class ExampleModelAdapter(
    private var listener: ItemClickListener
) : BaseAdapter<ExampleModel>(
    BaseDiffUtil(
        areItemsTheSameCustom = { oldItem, newItem -> oldItem.id == newItem.id },
        areContentsTheSameCustom = { oldItem, newItem -> oldItem == newItem }
    )
) {
    override fun createCustomViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemExampleModelBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_example_model,
            parent,
            false
        )
        return ExampleModelViewHolder(binding)
    }

    override fun bindCustomViewHolder(
        holder: RecyclerView.ViewHolder,
        item: ExampleModel,
        position: Int
    ) {
        (holder as ExampleModelViewHolder).bind(item, listener)
    }

}