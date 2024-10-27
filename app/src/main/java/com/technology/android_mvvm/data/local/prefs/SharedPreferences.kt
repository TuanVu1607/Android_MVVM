package com.technology.android_mvvm.data.local.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.technology.android_mvvm.data.local.prefs.PreferencesConstants

abstract class SharedPreferences(context: Context) {
    abstract fun getPrefName(): String

    protected val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(PreferencesConstants.KEYSTORE_NAME, MODE_PRIVATE)
    }
}