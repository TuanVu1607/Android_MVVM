package com.technology.android_mvvm.data.local.prefs

import com.technology.android_mvvm.data.local.db.entity.UserEntity

interface Preferences {
    fun getAccessToken(): String

    fun setAccessToken(accessToken: String)

    fun deleteAccessToken()

    fun getUserData(): UserEntity

    fun setUserData(userData: UserEntity)

    fun deleteUserData()

    fun isLogin(): Boolean
}