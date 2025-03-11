package com.technology.android_mvvm.data.remote.providers

import com.technology.android_mvvm.data.remote.api.example.ApiService
import retrofit2.Retrofit

object ApiServiceProvider {

    fun getApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
