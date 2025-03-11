package com.technology.android_mvvm.di.modules

import com.squareup.moshi.Moshi
import com.technology.android_mvvm.data.remote.providers.ConverterFactoryProvider
import com.technology.android_mvvm.data.remote.providers.MoshiBuilderProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Converter

@Module
@InstallIn(SingletonComponent::class)
object MoshiModule {

    @Provides
    fun provideMoshi(): Moshi = MoshiBuilderProvider.moshiBuilder.build()

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        ConverterFactoryProvider.getMoshiConverterFactory(moshi)
}