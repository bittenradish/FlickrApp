package de.example.challenge.flickrapp.adapters.recycler

interface AdapterItem {
    val HISTORY_TYPE: Int
        get() = 1

    val PHOTO_TYPE: Int
        get() = 2

    fun getItemType(): Int
}