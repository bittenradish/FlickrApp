package de.example.challenge.flickrapp.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: AdapterItem, onClickedAction: OnItemClickedListener)
}