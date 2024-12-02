package com.technology.android_mvvm.di.modules

import android.content.Context
import com.technology.android_mvvm.domain.repository.UserPreferencesRepository
import com.technology.android_mvvm.data.local.prefs.EncryptedUserSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferencesRepository =
        EncryptedUserSharedPreferences(context)
}