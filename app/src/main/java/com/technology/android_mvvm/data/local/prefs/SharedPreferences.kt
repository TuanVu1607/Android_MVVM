package com.technology.android_mvvm.data.local.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

abstract class SharedPreferences(context: Context) {
    protected abstract fun getPrefName(): String

    protected val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(getPrefName(), MODE_PRIVATE)
    }
}