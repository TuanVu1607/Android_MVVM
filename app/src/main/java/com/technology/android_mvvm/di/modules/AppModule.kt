package com.technology.android_mvvm.di.modules

import com.technology.android_mvvm.data.remote.api.example.ApiService
import com.technology.android_mvvm.data.repository.MainRepositoryImp
import com.technology.android_mvvm.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    internal fun provideMainRepository(): MainRepository {
        return MainRepositoryImp()
    }
}