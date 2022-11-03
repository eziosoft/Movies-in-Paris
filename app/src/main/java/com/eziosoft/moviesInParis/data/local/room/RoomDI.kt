package com.eziosoft.moviesInParis.data.local.room

import androidx.room.Room
import com.eziosoft.moviesInParis.data.local.LocalDbRepositoryImplLocal
import com.eziosoft.moviesInParis.domain.repository.LocalDatabaseRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val roomModule = module {
    single<MoviesDatabase> {
        Room.databaseBuilder(androidContext(), MoviesDatabase::class.java, "db").build()
    }

    single { get<MoviesDatabase>().movieDao() }
    single { get<MoviesDatabase>().roomMovieDetailsDao() }

    single<LocalDatabaseRepository> {
        LocalDbRepositoryImplLocal(movieDao = get(), openApiRepository = get(), get())
    }
}
