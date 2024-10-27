package com.technology.android_mvvm.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.technology.android_mvvm.data.local.db.entity.UserEntity
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date {
        return value?.let { Date(it) } ?: Date(System.currentTimeMillis())
    }

    @TypeConverter
    fun toTimestamp(value: Date?): Long {
        return value?.let { value.time } ?: System.currentTimeMillis()
    }

    @TypeConverter
    fun fromUserJson(value: UserEntity?): String {
        return value?.let { Gson().toJson(value) } ?: ""
    }

    @TypeConverter
    fun toUserJson(value: String?): UserEntity {
        return value?.let { Gson().fromJson(it, UserEntity::class.java) } ?: UserEntity()
    }
}