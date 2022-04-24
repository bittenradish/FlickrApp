package de.example.challenge.flickrapp.adapters.spinner

import de.example.challenge.flickrapp.adapters.recycler.OnItemClickedListener

fun interface OnSpinnerItemListener: OnItemClickedListener {
    fun onItemClicked(position: Int)
}