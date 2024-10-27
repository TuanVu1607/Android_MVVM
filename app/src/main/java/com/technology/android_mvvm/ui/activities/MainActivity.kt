package com.technology.android_mvvm.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.coroutineScope
import com.technology.android_mvvm.R
import com.technology.android_mvvm.common.process.Loader
import com.technology.android_mvvm.common.process.LoadingDialog
import com.technology.android_mvvm.databinding.ActivityMainBinding
import com.technology.android_mvvm.ui.base.BaseActivity
import com.technology.android_mvvm.ui.viewmodels.MainViewModel
import com.technology.android_mvvm.utils.LoggerUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val TAG = MainActivity::class.java.simpleName

    private val mainViewModel by viewModels<MainViewModel>()
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        LoggerUtils.i(TAG, "onCreate()")
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.mainViewModel = mainViewModel
        loadingDialog = LoadingDialog(this, lifecycle.coroutineScope, mainViewModel.loader)
    }
}