package com.technology.android_mvvm.data.remote.interceptors

import android.content.Context
import com.technology.android_mvvm.utils.NoConnectivityException
import com.technology.android_mvvm.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInterceptor @Inject constructor(@ApplicationContext private val context: Context) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.hasInternetConnection(context))
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }
}