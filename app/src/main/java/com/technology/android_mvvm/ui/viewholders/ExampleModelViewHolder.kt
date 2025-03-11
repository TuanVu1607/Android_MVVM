package com.technology.android_mvvm.ui.viewholders

import com.technology.android_mvvm.common.extensions.setOnSingleClickListener
import com.technology.android_mvvm.common.interfaces.ItemClickListener
import com.technology.android_mvvm.data.model.ExampleModel
import com.technology.android_mvvm.databinding.ItemExampleModelBinding
import com.technology.android_mvvm.ui.base.BaseViewHolder
import com.technology.android_mvvm.BR

class ExampleModelViewHolder(binding: ItemExampleModelBinding) :
    BaseViewHolder<ItemExampleModelBinding>(binding) {

    fun bind(
        item: ExampleModel,
        listener: ItemClickListener
    ) {
        binding.setVariable(BR.exampleModel, item)
        binding.executePendingBindings()

        binding.root.setOnSingleClickListener {
            listener.onClick(item)
        }
    }
}