package com.technology.android_mvvm.ui.viewmodels

import android.content.Context
import com.technology.android_mvvm.common.process.Loader
import com.technology.android_mvvm.domain.repository.MainRepository
import com.technology.android_mvvm.ui.base.BaseViewModel
import com.technology.android_mvvm.utils.LoggerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    val loader: Loader,
    private val mainRepository: MainRepository
) : BaseViewModel(context, loader) {
    private val TAG = MainViewModel::class.java.simpleName

    init {
        LoggerUtils.i(TAG, "initViewModel()")
    }
}