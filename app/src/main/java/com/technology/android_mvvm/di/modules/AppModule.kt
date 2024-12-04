package com.technology.android_mvvm.di.modules

import com.technology.android_mvvm.data.repository.MainRepositoryImp
import com.technology.android_mvvm.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    internal fun provideMainRepository(): MainRepository {
        return MainRepositoryImp()
    }
}