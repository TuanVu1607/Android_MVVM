package com.technology.android_mvvm.ui.base

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.technology.android_mvvm.utils.LoggerUtils

abstract class BaseDialog<VB : ViewBinding>(
    context: Context,
    private val bindingFactory: (LayoutInflater) -> VB,
    private val cancelable: Boolean = true
) : AlertDialog(context) {

    companion object {
        const val TAG = "BaseDialog"
    }

    protected lateinit var binding: VB

    init {
        LoggerUtils.i(TAG, "initDialog()")
        initDialog()
    }

    private fun initDialog() {
        binding = bindingFactory(layoutInflater)
        setView(binding.root)
        setCancelable(cancelable)
        create()
        window?.setBackgroundDrawable(ColorDrawable(0))
    }
}