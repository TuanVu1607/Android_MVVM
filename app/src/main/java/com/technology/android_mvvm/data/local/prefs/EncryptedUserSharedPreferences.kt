package com.technology.android_mvvm.data.local.prefs

import android.content.Context
import com.google.gson.Gson
import com.technology.android_mvvm.data.local.db.entity.UserEntity
import com.technology.android_mvvm.domain.repository.UserPreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

private const val KEYSTORE_NAME = "app_secret_shared_prefs_user"
private const val KEY_USER_DATA = "user_data"
private const val KEY_ACCESS_TOKEN = "user_access_token"

@Singleton
class EncryptedUserSharedPreferences @Inject constructor(applicationContext: Context) :
    BaseSharedPreferences(applicationContext),
    UserPreferencesRepository {

    override fun getPrefName() = KEYSTORE_NAME

    override fun getAccessToken(): String = get<String>(KEY_ACCESS_TOKEN) ?: ""

    override fun setAccessToken(accessToken: String) = set(KEY_ACCESS_TOKEN, accessToken)

    override fun deleteAccessToken() = remove(KEY_ACCESS_TOKEN)

    override fun getUserData(): UserEntity {
        val userDataCache = get<String>(KEY_USER_DATA)
        return if (!userDataCache.isNullOrEmpty()) {
            Gson().fromJson(userDataCache, UserEntity::class.java)
        } else {
            UserEntity()
        }
    }

    override fun setUserData(userData: UserEntity) = set(KEY_USER_DATA, Gson().toJson(userData))

    override fun deleteUserData() = remove(KEY_USER_DATA)

    override fun isLogin() = getUserData().id > 0
}