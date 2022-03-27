package de.example.challenge.flickrapp.adapter

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import de.example.challenge.flickrapp.R
import de.example.challenge.flickrapp.database.RequestHistoryModel

class HistoryViewHolder(@NonNull itemView: View) : AbstractViewHolder(itemView) {
    private var savedRequestTextView: TextView = itemView.findViewById(R.id.savedRequestTextView)
    private var deleteRequestButton: Button = itemView.findViewById(R.id.deleteRequestButton)

    override fun bind(@NonNull item: AdapterItem, onClickedAction: OnItemClickedListener) {
        val historyItem: RequestHistoryModel = item as RequestHistoryModel
        val historyItemAction: OnHistoryItemListener = onClickedAction as OnHistoryItemListener
        savedRequestTextView.text = historyItem.request

        itemView.setOnClickListener {
            historyItemAction.onItemClicked()
        }
        deleteRequestButton.setOnClickListener {
            historyItemAction.deleteButtonClicked(historyItem.request)
        }

    }
}