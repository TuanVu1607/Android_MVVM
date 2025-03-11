package com.technology.android_mvvm.data.remote.providers

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date

object MoshiBuilderProvider {
    val moshiBuilder: Moshi.Builder = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .add(KotlinJsonAdapterFactory())
}