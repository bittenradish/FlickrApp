package de.example.challenge.flickrapp.domain.repository

import androidx.lifecycle.LiveData
import de.example.challenge.flickrapp.data.repository.database.RequestHistoryModel

interface UserDataBaseRepository {

    //TODO: return Boolean
    fun addRequestToDb(requestText: String)

    //TODO: return Boolean
    fun deleteRequestByString(requestText: String)

    //TODO: return Boolean
    fun clearRequestHistory()

    fun getRequestHistory(): LiveData<List<RequestHistoryModel>>
}