package de.example.challenge.flickrapp.domain.usecases

import de.example.challenge.flickrapp.data.repository.flickrapi.models.SortEnum
import de.example.challenge.flickrapp.domain.model.searchmodels.SearchResultModel
import de.example.challenge.flickrapp.domain.repository.FlickrApiRepository

class SearchPhotoUseCase(private val flickrApiRepository: FlickrApiRepository) {

    suspend fun execute(page: Int = 1, requestText: String, sort: SortEnum = SortEnum.RELEVANCE, apiKey: String ): SearchResultModel {
        return flickrApiRepository.searchPhotos(page, requestText, sort, apiKey)
    }
}