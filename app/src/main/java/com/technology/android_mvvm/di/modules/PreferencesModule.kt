package com.technology.android_mvvm.di.modules

import android.content.Context
import com.technology.android_mvvm.data.local.prefs.PreferenceManager
import com.technology.android_mvvm.data.local.prefs.Preferences
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
    fun providePreferences(@ApplicationContext context: Context): Preferences =
        PreferenceManager(context)
}