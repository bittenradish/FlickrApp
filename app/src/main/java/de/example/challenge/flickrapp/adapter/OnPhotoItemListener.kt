package de.example.challenge.flickrapp.adapter

fun interface OnPhotoItemListener : OnItemClickedListener {
    fun onItemClicked(position: Int)
}