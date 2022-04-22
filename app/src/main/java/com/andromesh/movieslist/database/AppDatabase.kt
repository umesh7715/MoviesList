package com.andromesh.movieslist.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andromesh.movieslist.movies.data.Movie
import com.andromesh.movieslist.movies.data.MovieDao
import com.andromesh.movieslist.movies.data.MovieDetail

/**
 * The Room database for this app
 */
@Database(
    entities = [Movie::class, MovieDetail::class],
    version = 1, exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao
}
