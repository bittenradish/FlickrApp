package de.example.challenge.flickrapp.adapters.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: AdapterItem, onClickedAction: OnItemClickedListener)
}