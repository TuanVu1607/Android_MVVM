package com.technology.android_mvvm.domain.repository

import com.technology.android_mvvm.data.model.ExampleModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getExampleModels(): Flow<List<ExampleModel>>

    fun getExampleDetail(id: Int): Flow<ExampleModel>
}