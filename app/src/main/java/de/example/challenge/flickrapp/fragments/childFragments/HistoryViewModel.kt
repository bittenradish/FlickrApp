package de.example.challenge.flickrapp.fragments.childFragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.example.challenge.flickrapp.application.App
import de.example.challenge.flickrapp.database.RequestDao
import de.example.challenge.flickrapp.database.RequestHistoryModel
import de.example.challenge.flickrapp.executors.AppExecutors

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val requestDao: RequestDao = App.getAppInstance().getDataBase().requestDao()
    private var requestHistoryLiveData: LiveData<List<RequestHistoryModel>>? = null

    fun getRequestsHistory(): LiveData<List<RequestHistoryModel>> {
        if (requestHistoryLiveData == null) {
            requestHistoryLiveData = requestDao.getAll()
        }
        return requestHistoryLiveData as LiveData<List<RequestHistoryModel>>
    }

    fun deleteRequest(requestText: String) {
        AppExecutors.diskIO().execute(Runnable {
            requestDao.delete(requestText)
        })
    }

    fun clearRequestHistory() {
        AppExecutors.diskIO().execute(Runnable {
            requestDao.clearDB()
        })
    }
}