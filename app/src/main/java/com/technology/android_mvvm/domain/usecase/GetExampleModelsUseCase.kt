package com.technology.android_mvvm.domain.usecase

import com.technology.android_mvvm.data.model.ExampleModel
import com.technology.android_mvvm.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExampleModelsUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    operator fun invoke(): Flow<List<ExampleModel>> = homeRepository.getExampleModels()
}