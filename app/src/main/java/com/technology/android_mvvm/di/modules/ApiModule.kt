package com.technology.android_mvvm.di.modules

import com.technology.android_mvvm.BuildConfig
import com.technology.android_mvvm.data.remote.api.example.ApiService
import com.technology.android_mvvm.data.remote.providers.ApiServiceProvider.getApiService
import com.technology.android_mvvm.data.remote.providers.RetrofitProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideBaseApiUrl() = BuildConfig.HOST_URL

    @Provides
    fun provideRetrofit(
        baseApiUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(baseApiUrl, okHttpClient, converterFactory)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = getApiService(retrofit)
}