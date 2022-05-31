package de.example.challenge.flickrapp.domain.repository

import de.example.challenge.flickrapp.data.repository.flickrapi.models.PhotoModel
import de.example.challenge.flickrapp.data.repository.flickrapi.models.SortEnum

interface FlickrApiRepository {
    fun searchPhotos(page: Int, requestText: String, sort: SortEnum):List<PhotoModel>
}