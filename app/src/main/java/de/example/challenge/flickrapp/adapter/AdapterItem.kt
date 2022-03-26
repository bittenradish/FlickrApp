package de.example.challenge.flickrapp.adapter

interface AdapterItem {
    val HISTORY_TYPE: Int
        get() = 1

    val PHOTO_TYPE: Int
        get() = 2

    fun getItemType(): Int
}