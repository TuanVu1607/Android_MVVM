package com.technology.android_mvvm.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.coroutineScope
import com.technology.android_mvvm.ui.dialogs.LoadingDialog
import com.technology.android_mvvm.databinding.ActivityMainBinding
import com.technology.android_mvvm.ui.base.BaseActivity
import com.technology.android_mvvm.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = { inflater -> ActivityMainBinding.inflate(inflater) }

    override val viewModel by viewModels<MainViewModel>()

    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.mainViewModel = viewModel
        loadingDialog = LoadingDialog(this, lifecycle.coroutineScope, viewModel.loader)
    }
}