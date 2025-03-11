package com.technology.android_mvvm.ui.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.technology.android_mvvm.data.model.ExampleModel
import com.technology.android_mvvm.domain.usecase.GetExampleModelsUseCase
import com.technology.android_mvvm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    getExampleModelsUseCase: GetExampleModelsUseCase
) : BaseViewModel(context) {

    private val _exampleModels = MutableStateFlow<List<ExampleModel>>(emptyList())
    val exampleModels = _exampleModels.asStateFlow()

    init {
        getExampleModelsUseCase()
            .launchNetwork()
            .onEach { result -> _exampleModels.value = result }
            .launchIn(viewModelScope)
    }
}