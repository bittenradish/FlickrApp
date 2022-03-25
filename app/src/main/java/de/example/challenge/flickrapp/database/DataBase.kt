package de.example.challenge.flickrapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RequestHistoryModel::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun requestDao(): RequestDao
}