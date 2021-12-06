package io.github.jwgibanez.contacts.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jwgibanez.contacts.data.db.AppDatabase
import io.github.jwgibanez.contacts.data.db.AppDatabase.Companion.getInstance

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return getInstance(appContext)
    }
}