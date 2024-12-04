package com.technology.android_mvvm.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.technology.android_mvvm.BuildConfig
import com.technology.android_mvvm.data.remote.interceptors.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val NETWORK_CALL_TIMEOUT = 30L

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ) = ChuckerInterceptor.Builder(context)
        .collector(
            ChuckerCollector(
                context = context,
                showNotification = true,
                retentionPeriod = RetentionManager.Period.ONE_HOUR
            )
        )
        .alwaysReadResponseBody(true)
        .build()

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
    ) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(loggingInterceptor)
            addInterceptor(networkInterceptor)
            addInterceptor(chuckerInterceptor)
            readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
        }
        retryOnConnectionFailure(true)
    }.build()
}