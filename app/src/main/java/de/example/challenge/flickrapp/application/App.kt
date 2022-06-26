package de.example.challenge.flickrapp.application

import android.app.Application
import de.example.challenge.flickrapp.di.appModule
import de.example.challenge.flickrapp.di.dataModule
import de.example.challenge.flickrapp.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    companion object {
        private lateinit var instance: App

        fun getAppInstance(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(getAppInstance())
            modules(listOf(appModule, domainModule, dataModule))
        }
    }
}