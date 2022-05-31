package de.example.challenge.flickrapp.domain.usecases

import androidx.lifecycle.LiveData
import de.example.challenge.flickrapp.data.repository.database.RequestHistoryModel
import de.example.challenge.flickrapp.domain.repository.UserDataBaseRepository

class GetHistoryUseCase(private val userDataBaseRepository: UserDataBaseRepository) {
    fun execute(): LiveData<List<RequestHistoryModel>>{
        return userDataBaseRepository.getRequestHistory()
    }
}