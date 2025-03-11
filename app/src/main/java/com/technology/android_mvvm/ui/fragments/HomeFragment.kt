package com.technology.android_mvvm.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technology.android_mvvm.common.interfaces.ItemClickListener
import com.technology.android_mvvm.data.model.ExampleModel
import com.technology.android_mvvm.databinding.FragmentHomeBinding
import com.technology.android_mvvm.ui.adapters.ExampleModelAdapter
import com.technology.android_mvvm.ui.base.BaseFragment
import com.technology.android_mvvm.ui.viewmodels.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), ItemClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = { inflater, container, attachToParent ->
            FragmentHomeBinding.inflate(inflater, container, attachToParent)
        }

    private lateinit var exampleModelAdapter: ExampleModelAdapter

    override fun setupView() {
        initAdapter()
    }

    private fun initAdapter() {
        exampleModelAdapter = ExampleModelAdapter(this)
        exampleModelAdapter.submitList(emptyList())
        binding.rcvExampleModel.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    binding.rcvExampleModel.context,
                    RecyclerView.VERTICAL
                )
            )
            adapter = exampleModelAdapter
        }
    }

    override fun bindViewModel() {
        viewModel.exampleModels bindTo ::displayExampleModels
    }

    private fun displayExampleModels(exampleModels: List<ExampleModel>) {
        exampleModelAdapter.submitList(exampleModels)
    }

    override fun onClick(objects: Any?) {
        //
    }
}