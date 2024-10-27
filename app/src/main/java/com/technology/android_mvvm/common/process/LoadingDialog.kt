package com.technology.android_mvvm.common.process

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.lifecycle.LifecycleCoroutineScope
import com.technology.android_mvvm.databinding.LoadingDialogBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoadingDialog(
    private val activity: Activity,
    private val lifeCycleScope: LifecycleCoroutineScope,
    private val loader: Loader
) {
    private val binding = LoadingDialogBinding.inflate(LayoutInflater.from(activity))
    private lateinit var alertDialog: AlertDialog

    init {
        initDialog()
        listenLoader()
    }

    private fun initDialog() {
        alertDialog = AlertDialog.Builder(activity)
            .setView(binding.root)
            .setCancelable(false)
            .create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))
    }

    private fun listenLoader() = lifeCycleScope.launch {
        loader.loading.collectLatest { loading ->
            if (loading) {
                if (!alertDialog.isShowing) alertDialog.show()
            } else {
                if (alertDialog.isShowing) alertDialog.dismiss()
            }
        }
    }
}