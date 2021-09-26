package com.envious.watchlist.ui

import android.app.Application
import com.envious.data.di.databaseModule
import com.envious.data.di.networkingModule
import com.envious.data.di.repositoryModule
import com.envious.domain.di.useCaseModule
import com.envious.watchlist.BuildConfig
import com.envious.watchlist.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WatchListApplication : Application() {

    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@WatchListApplication)
            if (BuildConfig.DEBUG) androidLogger(Level.ERROR)
            modules(appModules + domainModules + dataModules)
        }
    }
}

val appModules = listOf(presentationModule)
val domainModules = listOf(useCaseModule)
val dataModules = listOf(networkingModule, repositoryModule, databaseModule)