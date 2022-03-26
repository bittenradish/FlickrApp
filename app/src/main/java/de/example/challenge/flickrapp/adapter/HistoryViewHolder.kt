package de.example.challenge.flickrapp.adapter

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.database.RequestHistoryModel

class HistoryViewHolder(@NonNull itemView: View) : AbstractViewHolder(itemView) {
    private lateinit var savedRequestTextView: TextView

    init {
        savedRequestTextView = itemView.findViewById(R.id.savedRequestTextView)
    }

    override fun bind(@NonNull item: AdapterItem, onClickedAction: OnItemClickedListener) {
        val historyItem: RequestHistoryModel = item as RequestHistoryModel
        savedRequestTextView.text = historyItem.request
        itemView.setOnClickListener {
            onClickedAction.onItemClicked()
        }
    }
}