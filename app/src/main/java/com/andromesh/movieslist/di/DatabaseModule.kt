package com.andromesh.movieslistassignment.di

import android.content.Context
import androidx.room.Room
import com.andromesh.movieslist.database.AppDatabase
import com.andromesh.movieslist.movies.data.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "movies_database"
        ).build()
    }

    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.moviesDao()
    }
}