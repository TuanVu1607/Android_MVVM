package com.technology.android_mvvm.data.remote.providers

import com.squareup.moshi.Moshi
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory

object ConverterFactoryProvider {

    fun getMoshiConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiConverterFactory.create(moshi)
}
