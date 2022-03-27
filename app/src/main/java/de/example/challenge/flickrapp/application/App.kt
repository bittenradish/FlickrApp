package de.example.challenge.flickrapp.application

import android.app.Application
import androidx.room.Room
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.database.DataBase

class App : Application() {
    companion object {
        private lateinit var instance: App
        private lateinit var dataBase: DataBase
        lateinit var API_KEY: String

        fun getAppInstance(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dataBase = Room.databaseBuilder(this, DataBase::class.java, "searchingDataBase").build()
        API_KEY = this.resources.getString(R.string.API_KEY)
    }

    fun getDataBase(): DataBase {
        return dataBase
    }
}