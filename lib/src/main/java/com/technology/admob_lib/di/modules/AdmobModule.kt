package com.technology.admob_lib.di.modules

import android.content.Context
import com.technology.admob_lib.data.datasources.AdmobDatasource
import com.technology.admob_lib.data.repositories.AdmobRepositoryImpl
import com.technology.admob_lib.domain.repositories.AdmobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdmobModule {

    @Provides
    @Singleton
    fun provideAdmobDatasource(@ApplicationContext appContext: Context) =
        AdmobDatasource(appContext)

    @Provides
    @Singleton
    fun provideAdmobRepository(admobDatasource: AdmobDatasource): AdmobRepository =
        AdmobRepositoryImpl(admobDatasource)
}