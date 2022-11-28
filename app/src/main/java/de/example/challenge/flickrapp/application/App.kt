package de.example.challenge.flickrapp.application

import android.app.Application
import de.example.challenge.flickrapp.di.*
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

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(instance))
            .build()
//        startKoin {
//            androidLogger(Level.DEBUG)
//            androidContext(getAppInstance())
//            modules(listOf(appModule, domainModule, dataModule))
//        }
    }
}