package com.technology.android_mvvm.data.repository

import com.technology.android_mvvm.data.model.ExampleModel
import com.technology.android_mvvm.data.remote.api.example.ApiService
import com.technology.android_mvvm.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImp @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {

    override fun getExampleModels(): Flow<List<ExampleModel>> = flow {
        runCatching { apiService.getExampleList() }
            .onSuccess { result -> emit(result.data) }
            .onFailure { exception -> throw exception }
    }

    override fun getExampleDetail(id: Int): Flow<ExampleModel> = flow {
        runCatching { apiService.getExampleDetail(id) }
            .onSuccess { result -> emit(result.data) }
            .onFailure { exception -> throw exception }
    }
}