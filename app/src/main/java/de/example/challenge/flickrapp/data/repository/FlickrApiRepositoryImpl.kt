package de.example.challenge.flickrapp.data.repository

import de.example.challenge.flickrapp.data.repository.flickrapi.models.PhotoModel
import de.example.challenge.flickrapp.data.repository.flickrapi.models.SortEnum
import de.example.challenge.flickrapp.domain.repository.FlickrApiRepository

class FlickrApiRepositoryImpl: FlickrApiRepository {

    override fun searchPhotos(page: Int, requestText: String, sort: SortEnum): List<PhotoModel> {
        TODO("Not yet implemented")
    }
}