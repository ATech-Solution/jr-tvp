package com.atech.android.di

import android.content.Context
import androidx.room.Room
import com.atech.android.BuildConfig
import com.atech.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.APPLICATION_ID)
            .build()

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase) = appDatabase.movieDao()
}