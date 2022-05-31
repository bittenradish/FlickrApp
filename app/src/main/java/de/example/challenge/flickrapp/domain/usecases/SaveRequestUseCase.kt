package de.example.challenge.flickrapp.domain.usecases

import de.example.challenge.flickrapp.domain.repository.UserDataBaseRepository

class SaveRequestUseCase(private val userDataBaseRepository: UserDataBaseRepository) {

    fun execute(requestText: String){
        userDataBaseRepository.addRequestToDb(requestText)
    }
}