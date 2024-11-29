package com.technology.android_mvvm.di.modules

import android.app.Application
import androidx.room.Room
import com.technology.android_mvvm.data.local.db.AppDatabase
import com.technology.android_mvvm.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: AppDatabase.Callback): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppConstants.APP_DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()
}