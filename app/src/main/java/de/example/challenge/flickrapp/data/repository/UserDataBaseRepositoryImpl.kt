package de.example.challenge.flickrapp.data.repository

import androidx.lifecycle.LiveData
import de.example.challenge.flickrapp.data.repository.database.DataBaseRepository
import de.example.challenge.flickrapp.data.repository.database.RequestHistoryModel
import de.example.challenge.flickrapp.domain.repository.UserDataBaseRepository

class UserDataBaseRepositoryImpl : UserDataBaseRepository {
    override fun addRequestToDb(requestText: String){
        DataBaseRepository.addRequest(requestText)
    }

    override fun deleteRequestByString(requestText: String) {
        DataBaseRepository.deleteRequest(requestText)
    }

    override fun clearRequestHistory(){
        DataBaseRepository.clearRequestHistory()
    }

    override fun getRequestHistory(): LiveData<List<RequestHistoryModel>> {
        return DataBaseRepository.getAllRequests()
    }
}