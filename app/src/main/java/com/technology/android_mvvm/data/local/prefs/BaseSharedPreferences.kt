package com.technology.android_mvvm.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.technology.android_mvvm.utils.LoggerUtils

abstract class BaseSharedPreferences(applicationContext: Context) {

    companion object {
        protected const val UNSUPPORTED_TYPE = "UNSUPPORTED_TYPE"
    }

    protected abstract fun getPrefName(): String

    protected val preferences: SharedPreferences by lazy {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            getPrefName(),
            masterKey,
            applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    protected inline fun <reified T> get(key: String): T? =
        if (preferences.contains(key)) {
            when (T::class) {
                Boolean::class -> preferences.getBoolean(key, false) as T?
                String::class -> preferences.getString(key, "") as T?
                Float::class -> preferences.getFloat(key, 0f) as T?
                Int::class -> preferences.getInt(key, 0) as T?
                Long::class -> preferences.getLong(key, 0L) as T?
                else -> null
            }
        } else {
            null
        }

    protected inline fun <reified T> set(key: String, value: T) {
        preferences.execute {
            when (value) {
                is Boolean -> it.putBoolean(key, value)
                is String -> it.putString(key, value)
                is Float -> it.putFloat(key, value)
                is Int -> it.putInt(key, value)
                is Long -> it.putLong(key, value)
                else -> LoggerUtils.e(UNSUPPORTED_TYPE, "Unsupported type")
            }
        }
    }

    protected fun remove(key: String) {
        preferences.execute { it.remove(key) }
    }

    protected fun clearAll() {
        preferences.execute { it.clear() }
    }
}