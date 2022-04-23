package de.example.challenge.flickrapp.adapters.recycler

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.NonNull
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.database.RequestHistoryModel

class HistoryViewHolder(@NonNull itemView: View) : AbstractViewHolder(itemView) {
    private var savedRequestTextView: TextView = itemView.findViewById(R.id.savedRequestTextView)
    private var deleteRequestButton: ImageButton = itemView.findViewById(R.id.deleteRequestButton)

    override fun bind(@NonNull item: AdapterItem, onClickedAction: OnItemClickedListener) {
        val historyItem: RequestHistoryModel = item as RequestHistoryModel
        val historyItemAction: OnHistoryItemListener = onClickedAction as OnHistoryItemListener
        savedRequestTextView.text = historyItem.request

        itemView.setOnClickListener {
            historyItemAction.onItemClicked(historyItem.request)
        }
        deleteRequestButton.setOnClickListener {
            historyItemAction.deleteButtonClicked(historyItem.request)
        }

    }
}