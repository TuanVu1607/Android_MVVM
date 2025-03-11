package com.technology.android_mvvm.di.modules

import com.technology.android_mvvm.data.remote.api.example.ApiService
import com.technology.android_mvvm.data.repository.HomeRepositoryImp
import com.technology.android_mvvm.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    internal fun provideHomeRepository(apiService: ApiService): HomeRepository {
        return HomeRepositoryImp(apiService)
    }
}