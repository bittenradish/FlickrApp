package de.example.challenge.flickrapp.application

import android.app.Application

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
    }
}