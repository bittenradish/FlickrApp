package de.example.challenge.flickrapp.domain.usecases

import de.example.challenge.flickrapp.domain.repository.UserDataBaseRepository

class DeleteRequestUseCase(private val userDataBaseRepository: UserDataBaseRepository) {

    fun execute(requestText: String){
        userDataBaseRepository.deleteRequestByString(requestText)
    }
}