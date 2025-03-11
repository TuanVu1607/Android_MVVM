package com.technology.android_mvvm.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String = "",
)