package de.example.challenge.flickrapp.data.repository.flickrapi.models

import de.example.challenge.flickrapp.adapters.recycler.AdapterItem

data class PhotoModel(
    var id: String, var owner: String, var secret: String, var server: String,
    var farm: Int, var title: String
) : AdapterItem {
    override fun getItemType(): Int {
        return PHOTO_TYPE
    }

    fun getPhotoUrl(): String {
        val photoBaseUrl = "https://live.staticflickr.com/"
        return StringBuilder().apply {
            append(photoBaseUrl)
            append(server)
            append("/")
            append(id)
            append("_")
            append(secret)
            append(".jpg")
        }.toString()
    }
}