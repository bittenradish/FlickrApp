package de.example.challenge.flickrapp.application

import android.app.Application
import androidx.room.Room
import de.example.challenge.flickrapp.database.DataBase

class App : Application() {
    companion object {
        private lateinit var instance: App
        private lateinit var dataBase: DataBase

        fun getAppInstance(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataBase = Room.databaseBuilder(this, DataBase::class.java, "searchingDataBase").build()
    }

    fun getDataBase(): DataBase {
        return dataBase
    }
}