package de.example.challenge.flickrapp.adapters.recycler

interface OnHistoryItemListener : OnItemClickedListener {
    fun deleteButtonClicked(requestText: String)
    fun onItemClicked(requestText: String)
}