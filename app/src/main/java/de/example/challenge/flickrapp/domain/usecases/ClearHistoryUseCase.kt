package de.example.challenge.flickrapp.domain.usecases

import de.example.challenge.flickrapp.domain.repository.UserDataBaseRepository

class ClearHistoryUseCase(private val userDataBaseRepository: UserDataBaseRepository) {
    fun execute(){
        userDataBaseRepository.clearRequestHistory()
    }
}