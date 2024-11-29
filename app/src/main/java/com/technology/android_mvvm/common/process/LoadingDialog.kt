package com.technology.android_mvvm.common.process

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleCoroutineScope
import com.technology.android_mvvm.databinding.LoadingDialogBinding
import com.technology.android_mvvm.ui.base.BaseDialog
import com.technology.android_mvvm.utils.LoggerUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoadingDialog(
    context: Context,
    private val lifeCycleScope: LifecycleCoroutineScope,
    private val loader: Loader
) : BaseDialog<LoadingDialogBinding>(
    context,
    LoadingDialogBinding::inflate,
    false
) {
    private val TAG = LoadingDialog::class.java.simpleName

    init {
        LoggerUtils.i(TAG, "initDialog()")
        listenLoader()
    }

    private fun listenLoader() = lifeCycleScope.launch {
        loader.loading.collectLatest { loading ->
            if (loading) {
                if (!isShowing) show()
            } else {
                if (isShowing) dismiss()
            }
        }
    }
}