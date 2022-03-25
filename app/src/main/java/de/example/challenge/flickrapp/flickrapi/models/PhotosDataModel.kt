package de.example.challenge.flickrapp.flickrapi.models

data class PhotosDataModel(var page: Int, var pages: Int, var total: Int, var photo: MutableList<PhotoModel>) {
}