package de.example.challenge.flickrapp.adapters.recycler

fun interface OnPhotoItemListener : OnItemClickedListener {
    fun onItemClicked(position: Int)
}