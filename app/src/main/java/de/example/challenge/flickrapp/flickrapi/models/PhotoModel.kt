package de.example.challenge.flickrapp.flickrapi.models

data class PhotoModel(var id: String, var owner: String, var secret: String, var server: String,
                 var farm: Int, var title: String) {
}