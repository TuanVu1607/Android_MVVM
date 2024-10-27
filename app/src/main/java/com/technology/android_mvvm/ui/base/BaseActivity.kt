package com.technology.android_mvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.technology.android_mvvm.data.local.prefs.SharedPreferences
import com.technology.android_mvvm.utils.LoggerUtils

abstract class BaseActivity<VB : ViewBinding>(private val bindingFactory: (LayoutInflater) -> VB) : AppCompatActivity() {

    companion object{
        const val TAG = "BaseActivity"
    }

    protected lateinit var binding: VB
    protected lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoggerUtils.i(TAG, "onCreate()")
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }
}