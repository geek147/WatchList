package com.envious.data.di

import androidx.room.Room
import com.envious.data.local.db.WatchListDatabase
import com.envious.data.util.Constants.WATCH_LIST_DB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), WatchListDatabase::class.java, WATCH_LIST_DB)
            .fallbackToDestructiveMigration().build()
    }

    factory {
        get<WatchListDatabase>().watchListDao
    }
}
