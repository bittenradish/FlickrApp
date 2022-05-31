package de.example.challenge.flickrapp.data.repository.database

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.room.Room
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.executors.AppExecutors

object DataBaseRepository {
    private val dataBase: DataBase = Room.databaseBuilder(App.getAppInstance(), DataBase::class.java, "searchingDataBase").build()

    fun deleteRequest(requestText: String) {
        AppExecutors.diskIO().execute(Runnable {
            //TODO return boolean
            dataBase.requestDao().delete(requestText)
        })
    }

    fun clearRequestHistory() {
        AppExecutors.diskIO().execute(Runnable {
            //TODO return boolean
            dataBase.requestDao().clearDB()
        })
    }

    fun getAllRequests(): LiveData<List<RequestHistoryModel>> {
        return dataBase.requestDao().getAll()
    }

    fun addRequest(textOfRequest: String){
        AppExecutors.diskIO().execute(Runnable {
            try {
                //TODO return boolean
                dataBase.requestDao().add(RequestHistoryModel(textOfRequest))
            } catch (ex: SQLiteConstraintException) {
            }
        })
    }
}