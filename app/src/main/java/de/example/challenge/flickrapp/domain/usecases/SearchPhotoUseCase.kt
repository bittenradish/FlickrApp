package de.example.challenge.flickrapp.domain.usecases

import de.example.challenge.flickrapp.data.repository.flickrapi.models.PhotoModel
import de.example.challenge.flickrapp.data.repository.flickrapi.models.SortEnum
import de.example.challenge.flickrapp.domain.repository.FlickrApiRepository

class SearchPhotoUseCase(private val flickrApiRepository: FlickrApiRepository) {

    fun execute(page: Int = 1, requestText: String, sort: SortEnum = SortEnum.RELEVANCE ):List<PhotoModel> {
        return flickrApiRepository.searchPhotos(page, requestText, sort)
    }
}