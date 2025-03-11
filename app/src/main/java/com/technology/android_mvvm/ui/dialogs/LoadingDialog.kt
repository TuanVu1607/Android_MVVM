package com.technology.android_mvvm.ui.dialogs

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import com.technology.android_mvvm.ui.common.Loader
import com.technology.android_mvvm.databinding.LoadingDialogBinding
import com.technology.android_mvvm.ui.base.BaseDialog
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

    init {
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