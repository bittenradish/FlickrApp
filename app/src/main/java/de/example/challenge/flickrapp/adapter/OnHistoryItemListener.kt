package de.example.challenge.flickrapp.adapter

interface OnHistoryItemListener : OnItemClickedListener {
    fun deleteButtonClicked(requestText: String)
}