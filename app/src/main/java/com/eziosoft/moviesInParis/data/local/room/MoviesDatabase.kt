package com.eziosoft.moviesInParis.data.local.room

import androidx.room.RoomDatabase
import com.eziosoft.moviesInParis.data.local.room.movieDetails.LocalMovieDetails
import com.eziosoft.moviesInParis.data.local.room.movieDetails.MovieDetailsDao
import com.eziosoft.moviesInParis.data.local.room.movies.LocalMovie
import com.eziosoft.moviesInParis.data.local.room.movies.MovieDao

@androidx.room.Database(entities = [LocalMovie::class, LocalMovieDetails::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    abstract fun roomMovieDetailsDao(): MovieDetailsDao
}
