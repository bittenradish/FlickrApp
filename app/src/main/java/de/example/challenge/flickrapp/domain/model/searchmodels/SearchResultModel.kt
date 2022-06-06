package de.example.challenge.flickrapp.domain.model.searchmodels

import de.example.challenge.flickrapp.data.repository.flickrapi.ResponseCode
import de.example.challenge.flickrapp.data.repository.flickrapi.models.PhotoModel

data class SearchResultModel (var photos: MutableList<PhotoModel>, val responseCode: ResponseCode)