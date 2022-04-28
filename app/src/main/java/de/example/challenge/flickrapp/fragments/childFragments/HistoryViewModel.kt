package de.example.challenge.flickrapp.fragments.childFragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.example.challenge.flickrapp.database.DataBaseRepository
import de.example.challenge.flickrapp.database.RequestHistoryModel

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private var requestHistoryLiveData: LiveData<List<RequestHistoryModel>> = DataBaseRepository.getAllRequests()

    fun getRequestsHistory(): LiveData<List<RequestHistoryModel>> {
        return requestHistoryLiveData
    }

    fun deleteRequest(requestText: String) {
        DataBaseRepository.deleteRequest(requestText)
    }

    fun clearRequestHistory() {
        DataBaseRepository.clearRequestHistory()
    }
}