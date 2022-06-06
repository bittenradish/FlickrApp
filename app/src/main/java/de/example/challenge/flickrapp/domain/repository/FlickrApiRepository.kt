package de.example.challenge.flickrapp.domain.repository

import de.example.challenge.flickrapp.data.repository.flickrapi.models.SortEnum
import de.example.challenge.flickrapp.domain.model.searchmodels.SearchResultModel

interface FlickrApiRepository {
    suspend fun searchPhotos(page: Int, requestText: String, sort: SortEnum, apiKey: String): SearchResultModel
}