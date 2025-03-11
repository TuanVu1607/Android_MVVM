package com.technology.android_mvvm.ui.viewmodels

import android.content.Context
import com.technology.android_mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseViewModel(context) {

}