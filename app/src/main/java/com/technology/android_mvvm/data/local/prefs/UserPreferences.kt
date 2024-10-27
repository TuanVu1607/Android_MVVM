package com.technology.android_mvvm.data.local.prefs

import android.content.Context
import com.google.gson.Gson
import com.technology.android_mvvm.data.local.db.entity.UserEntity
import com.technology.android_mvvm.domain.repository.UserPreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(context: Context) : SharedPreferences(context),
    UserPreferencesRepository {

    companion object {
        private const val KEYSTORE_NAME = "userCache"
        private const val KEY_USER_DATA = "userData"
        private const val KEY_ACCESS_TOKEN = "userAccessToken"
    }

    override fun getPrefName() = KEYSTORE_NAME

    override fun getAccessToken(): String {
        val accessToken = preferences.getString(KEY_ACCESS_TOKEN, "")
        return if (!accessToken.isNullOrEmpty()) {
            accessToken
        } else {
            ""
        }
    }

    override fun setAccessToken(accessToken: String) {
        preferences.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply()
    }

    override fun deleteAccessToken() {
        preferences.edit().remove(KEY_ACCESS_TOKEN).apply()
    }

    override fun getUserData(): UserEntity {
        val userDataCache = preferences.getString(KEY_USER_DATA, "")
        return if (!userDataCache.isNullOrEmpty()) {
            Gson().fromJson(userDataCache, UserEntity::class.java)
        } else {
            UserEntity()
        }
    }

    override fun setUserData(userData: UserEntity) {
        preferences.edit()
            .putString(KEY_USER_DATA, Gson().toJson(userData))
            .apply()
    }

    override fun deleteUserData() {
        preferences.edit().remove(KEY_USER_DATA).apply()
    }

    override fun isLogin() = getUserData().id > 0
}