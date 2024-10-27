package com.technology.android_mvvm.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.technology.android_mvvm.data.local.db.dao.UserDao
import com.technology.android_mvvm.data.local.db.entity.UserEntity
import com.technology.android_mvvm.di.annotations.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        UserEntity::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    class Callback @Inject constructor(
        private val database: Provider<AppDatabase>,
        @ApplicationScope
        private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}